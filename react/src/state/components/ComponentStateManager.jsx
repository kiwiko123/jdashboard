import React, { useEffect, useReducer, useState } from 'react';
import PropTypes from 'prop-types';
import Broadcaster from '../Broadcaster';
import StateTransmitter from '../StateTransmitter';

let _global_id = 0;

const ComponentStateManager = ({
    component, broadcaster, canResolve, id,
}) => {
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
        return null;
    }

    const ComponentType = component;
    return (
        <ComponentType {...broadcasterState} />
    );
};

ComponentStateManager.propTypes = {
    component: PropTypes.elementType.isRequired,
    broadcaster: PropTypes.oneOfType([
        PropTypes.instanceOf(Broadcaster),
        PropTypes.instanceOf(StateTransmitter),
    ]).isRequired,

    // A function that takes in the broadcaster's state, and returns a boolean.
    // If this returns false, the component should not be rendered.
    canResolve: PropTypes.func,

    // An identifier used solely for debugging purposes, like setting a breakpoint conditional on the ID.
    id: PropTypes.string,
};

ComponentStateManager.defaultProps = {
    canResolve: () => true,
    id: null,
};

export default ComponentStateManager;