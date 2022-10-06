import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import logger from 'tools/monitoring/logging';
import StateManager from '../StateManager';

const ComponentStateManager = ({
    component, stateManager, canResolve, id, staticProps,
}) => {
    const [managerState, setManagerState] = useState(stateManager.state);
    useEffect(() => {
        const componentId = `ComponentStateManager-${stateManager.id}`;
        stateManager.__linkStateProcessor(
            componentId,
            ({ state }) => {
                setManagerState(state);
            },
        );

        return () => {
            stateManager.__unlink(componentId);
        };
    }, [stateManager]);

    if (stateManager.id !== managerState.__INTERNAL_STATE_MANAGER_ID) {
        logger.debug(`ComponentStateManager render mismatch; actual state manager is ${stateManager.id} but attempting to render with ${managerState.__INTERNAL_STATE_MANAGER_ID}`);
        return null;
    }

    if (!canResolve(managerState)) {
        return null;
    }

    const ComponentType = component;
    return (
        <ComponentType
            {...managerState}
            {...staticProps}
        />
    );
};

ComponentStateManager.propTypes = {
    component: PropTypes.elementType.isRequired,
    stateManager: PropTypes.instanceOf(StateManager).isRequired,

    // A function that takes in the manager's state, and returns a boolean.
    // If this returns false, the component should not be rendered.
    canResolve: PropTypes.func,

    // An identifier used solely for debugging purposes, like setting a breakpoint conditional on the ID.
    id: PropTypes.string,

    // Props that will be passed into the component. These will take precedent over the state manager's state.
    staticProps: PropTypes.object,
};

ComponentStateManager.defaultProps = {
    canResolve: () => true,
    id: null,
    staticProps: {},
};

export default ComponentStateManager;