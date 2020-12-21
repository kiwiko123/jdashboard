import React, { cloneElement, useEffect, useReducer, useState } from 'react';
import PropTypes from 'prop-types';
import { isEmpty } from 'lodash';
import Broadcaster from '../Broadcaster';
import logger from '../../common/js/logging';

let _global_id = 0;

function resolveSuccessfully() {
    return true;
}

const ComponentStateManager = ({
    children, component, broadcaster, canResolve, id,
}) => {
    if (!(children || component)) {
        logger.error('One of children or component must be provided.');
    }

    const [numericalId] = useState(_global_id++);
    const [, forceUpdate] = useReducer(i => i + 1, 0);
    useEffect(() => {
        broadcaster._setUpdater(forceUpdate, numericalId);
        return () => {
            broadcaster.removeUpdater(numericalId);
        };
    }, [broadcaster, numericalId]);

    const broadcasterState = broadcaster.getState();
    if (!canResolve(broadcasterState)) {
        setTimeout(forceUpdate, 250);
        return null;
    }

    if (component) {
        const ComponentType = component;
        return (
            <ComponentType {...broadcasterState} />
        );
    }

    // Legacy path.
    return isEmpty(broadcasterState) ? children : cloneElement(children, broadcasterState);
};

ComponentStateManager.propTypes = {
    // One of [children, component] is required.
    children: PropTypes.element, // Deprecated; prefer using `component`.
    component: PropTypes.elementType,

    broadcaster: PropTypes.instanceOf(Broadcaster).isRequired,

    // A function that takes in the broadcaster's state, and returns a boolean.
    // If this returns false, the component should not be rendered.
    canResolve: PropTypes.func,

    // An identifier used solely for debugging purposes, like setting a breakpoint conditional on the ID.
    id: PropTypes.string,
};

ComponentStateManager.defaultProps = {
    children: null,
    component: null,
    canResolve: resolveSuccessfully,
    id: null,
};

export default ComponentStateManager;