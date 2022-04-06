import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import StateManager from '../StateManager';

const RESOLVE_SUCCESSFULLY = () => true;

const DefaultComponentStateManager = ({
    component, stateManager, canResolve, id,
}) => {
    const [managerState, setManagerState] = useState(stateManager.state);
    useEffect(() => {
        const componentId = `ComponentStateManager-${stateManager.id}`;
        stateManager.setUp({
            id: componentId,
            processState: ({ state }) => {
                setManagerState(state);
            },
        });

        return () => {
            stateManager.tearDown({ id: componentId });
        };
    }, [stateManager, setManagerState]);

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

    // A function that takes in the manager's state, and returns a boolean.
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