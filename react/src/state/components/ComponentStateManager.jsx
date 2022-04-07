import React from 'react';
import PropTypes from 'prop-types';
import Broadcaster from '../Broadcaster';
import StateTransmitter from '../StateTransmitter';
import StateManager from '../StateManager';
import DefaultComponentStateManager from './DefaultComponentStateManager';
import BroadcasterComponentStateManager from './BroadcasterComponentStateManager';

const RESOLVE_SUCCESSFULLY = () => true;
const EMPTY_OBJECT = {};

const ComponentStateManager = ({
    component, stateManager, broadcaster, canResolve, id, staticProps,
}) => {
    if (broadcaster) {
        // Deprecated flow
        return (
            <BroadcasterComponentStateManager
                component={component}
                broadcaster={broadcaster}
                canResolve={canResolve}
                id={id}
            />
        );
    }

    return (
        <DefaultComponentStateManager
            component={component}
            stateManager={stateManager}
            canResolve={canResolve}
            id={id}
            staticProps={staticProps}
        />
    );
};

ComponentStateManager.propTypes = {
    component: PropTypes.elementType.isRequired,
    stateManager: PropTypes.instanceOf(StateManager),
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

    staticProps: PropTypes.object,
};

ComponentStateManager.defaultProps = {
    canResolve: RESOLVE_SUCCESSFULLY,
    id: null,
    staticProps: EMPTY_OBJECT,
};

export default ComponentStateManager;