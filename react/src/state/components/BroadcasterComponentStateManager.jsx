import React, { useEffect, useReducer, useState } from 'react';
import PropTypes from 'prop-types';
import Broadcaster from '../Broadcaster';
import StateTransmitter from '../StateTransmitter';

let _GLOBAL_ID = 0;
const RESOLVE_SUCCESSFULLY = () => true;

/**
 * @deprecated Prefer ComponentStateManager
 */
const BroadcasterComponentStateManager = ({
    component, broadcaster, canResolve, id,
}) => {
    const [numericalId] = useState(_GLOBAL_ID++);
    const [, forceUpdate] = useReducer(i => i + 1, 0);

    useEffect(() => {
        broadcaster._setUpdater(forceUpdate, numericalId);
        return () => {
            broadcaster.removeUpdater(numericalId);
        };
    }, [broadcaster, numericalId]);

    const managerState = broadcaster.getState();
    if (!canResolve(managerState)) {
        return null;
    }

    const ComponentType = component;
    return (
        <ComponentType {...managerState} />
    );
};

BroadcasterComponentStateManager.propTypes = {
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

BroadcasterComponentStateManager.defaultProps = {
    canResolve: RESOLVE_SUCCESSFULLY,
    id: null,
};

export default BroadcasterComponentStateManager;