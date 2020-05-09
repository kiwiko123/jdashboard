import logger from '../common/js/logging';

const ACTIONS = new Map();

function makeActionId(broadcasterId, actionName) {
    return `${broadcasterId}-${actionName}`;
}

function registerAction(broadcasterId, actionName, action) {
    const actionId = makeActionId(broadcasterId, actionName);
    const actions = ACTIONS.has(actionId)
        ? ACTIONS.get(actionId)
        : [];

    actions.push(action);
    ACTIONS.set(actionId, actions);
}

function invokeAction(broadcasterId, actionName, processor = action => action()) {
    const actionId = makeActionId(broadcasterId, actionName);
    if (!ACTIONS.has(actionId)) {
        logger.warn(`No invokable action found with ID ${actionId}`);
        return;
    }

    ACTIONS.get(actionId)
        .forEach(processor);
}

function removeAction(broadcasterId, actionName, broadcasterInstanceId) {
    const actionId = makeActionId(broadcasterId, actionName);
    if (!ACTIONS.has(actionId)) {
        logger.debug(`No action found with ID %{actionId}`);
        return;
    }

    const currentActions = ACTIONS.get(actionId);
    const updatedActions = ACTIONS.get(actionId)
        .filter(action => action.broadcasterInstanceId !== broadcasterInstanceId);
    ACTIONS.set(actionId, updatedActions);

    logger.debug(`Removed ${currentActions.length - updatedActions.length} actions`);
}

export {
    registerAction,
    invokeAction,
    removeAction,
};