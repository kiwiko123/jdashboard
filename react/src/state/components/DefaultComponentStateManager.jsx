import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import StateManager from '../StateManager';

let _GLOBAL_ID = 0;
const RESOLVE_SUCCESSFULLY = () => true;

const DefaultComponentStateManager = ({
    component, stateManager, canResolve, id,
}) => {
    const [numericalId] = useState(_GLOBAL_ID++);
    const [managerState, setManagerState] = useState(stateManager.getState());

    useEffect(() => {
        stateManager.setUp({
            id: numericalId,
            processState: ({ state }) => {
                setManagerState({ ...state });
            },
        });

        return () => {
            stateManager.tearDown({ id: numericalId });
        };
    }, [stateManager, setManagerState, numericalId]);

    if (!canResolve(managerState)) {
        return null;
    }

    const ComponentType = component;
    return (
        <ComponentType {...managerState} />
    );
};

DefaultComponentStateManager.propTypes = {
    component: PropTypes.elementType.isRequired,
    stateManager: PropTypes.instanceOf(StateManager).isRequired,

    // A function that takes in the broadcaster's state, and returns a boolean.
    // If this returns false, the component should not be rendered.
    canResolve: PropTypes.func,

    // An identifier used solely for debugging purposes, like setting a breakpoint conditional on the ID.
    id: PropTypes.string,
};

DefaultComponentStateManager.defaultProps = {
    canResolve: RESOLVE_SUCCESSFULLY,
    id: null,
};

export default DefaultComponentStateManager;