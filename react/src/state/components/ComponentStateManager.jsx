import React, { useEffect, useReducer, useState } from 'react';
import PropTypes from 'prop-types';
import Broadcaster from '../Broadcaster';
import StateTransmitter from '../StateTransmitter';
import StateManager from '../StateManager';

let _GLOBAL_ID = 0;
const RESOLVE_SUCCESSFULLY = () => true;

const ComponentStateManager = ({
    component, manager, broadcaster, canResolve, id,
}) => {
    const [numericalId] = useState(_GLOBAL_ID++);
    const [, forceUpdate] = useReducer(i => i + 1, 0);
    const stateManager = manager || broadcaster;

    useEffect(() => {
        stateManager._setUpdater(forceUpdate, numericalId);
        return () => {
            stateManager.removeUpdater(numericalId);
        };
    }, [stateManager, numericalId]);

    const managerState = stateManager.getState();
    if (!canResolve(managerState)) {
        return null;
    }

    const ComponentType = component;
    return (
        <ComponentType {...managerState} />
    );
};

ComponentStateManager.propTypes = {
    component: PropTypes.elementType.isRequired,
    manager: PropTypes.instanceOf(StateManager),
    // Deprecated; prefer `manager`.
    broadcaster: PropTypes.oneOfType([
        PropTypes.instanceOf(Broadcaster),
        PropTypes.instanceOf(StateTransmitter),
    ]),

    // A function that takes in the broadcaster's state, and returns a boolean.
    // If this returns false, the component should not be rendered.
    canResolve: PropTypes.func,

    // An identifier used solely for debugging purposes, like setting a breakpoint conditional on the ID.
    id: PropTypes.string,
};

ComponentStateManager.defaultProps = {
    canResolve: RESOLVE_SUCCESSFULLY,
    id: null,
};

export default ComponentStateManager;