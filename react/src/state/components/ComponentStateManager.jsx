import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import logger from 'tools/monitoring/logging';
import StateManager from '../StateManager';

const ComponentStateManager = ({
    component, stateManager, canResolve, id, staticProps,
}) => {
    const [managerState, setManagerState] = useState(stateManager.state);
    useEffect(() => {
        const componentId = `ComponentStateManager-${stateManager.__id}`;
        logger.debug(`[ComponentStateManager] Linking ${componentId}`);
        stateManager.__linkStateProcessor(
            componentId,
            ({ state }) => {
                setManagerState(state);
            },
        );
        if (stateManager.__setStateCount > 0) {
            // The state manager may have already made state updates before being linked to its component.
            // In that event, an update must be manually triggered so that the component renders with fresh data.
            // Without doing this, the component may sometimes render with the default (empty) state based on render
            // timing.
            stateManager.update();
        }

        return () => {
            logger.debug(`[ComponentStateManager] Unlinking ${componentId}`);
            stateManager.__unlink(componentId);
        };
    }, [stateManager]);

    if (stateManager.__id !== managerState.__INTERNAL_STATE_MANAGER_ID) {
        logger.debug(`ComponentStateManager render mismatch; actual state manager is ${stateManager.__id} but attempting to render ${component.name} with ${managerState.__INTERNAL_STATE_MANAGER_ID}`);
        // TODO what should we do in this case?
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