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
    children, component, broadcaster, canResolve,
}) => {
    if (!(children || component)) {
        logger.error('One of children or component must be provided.');
    }

    const [id] = useState(_global_id++);
    const [, forceUpdate] = useReducer(i => i + 1, 0);
    useEffect(() => {
        broadcaster._setUpdater(forceUpdate, id);
        return () => {
            broadcaster.removeUpdater(id);
        };
    }, [broadcaster, id]);

    const broadcasterState = broadcaster.getState();
    if (!canResolve(broadcasterState)) {
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
};

ComponentStateManager.defaultProps = {
    children: null,
    component: null,
    canResolve: resolveSuccessfully,
};

export default ComponentStateManager;