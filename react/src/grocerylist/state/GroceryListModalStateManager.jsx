import StateManager from 'state/StateManager';
import logger from 'tools/monitoring/logging';

export default class GroceryListModalStateManager extends StateManager {
    constructor() {
        super();
        this.setState({
            isOpen: false,
            modalId: null,
        });
        this.registerMethod(this.close);
    }

    open(modalId) {
        this.setState({
            isOpen: true,
            modalId,
        });
    }

    close(modalId) {
        this.setState({
            isOpen: false,
            modalId: null,
        });
    }

    receiveGroceryListToolbarStateManager(state, metadata) {
        switch (metadata) {
            case 'pressCreateGroceryListButton':
                this.open('createGroceryList');
                return;
            default:
                logger.warn(`Unknown metadata from GroceryListToolbarStateManager ${metadata}`);
        }
    }

    receiveGroceryListFeedStateManager(state, metadata) {
        switch (metadata) {
            case 'editGroceryList':
                this.addState({
                    data: { groceryListId: state.groceryListId },
                });
                this.open('editGroceryList');
                return;
            default:
                logger.warn('Unknown metadata from GroceryListFeedStateManager');
        }
    }

    receiveCreateGroceryListFormStateManager(state, metadata) {
        switch (metadata) {
            case 'close':
                this.close('createGroceryList');
                break;
        }
    }

    receiveEditGroceryListFormStateManager(state, metadata) {
        switch (metadata) {
            case 'close':
                this.close('editGroceryList');
                break;
        }
    }
}