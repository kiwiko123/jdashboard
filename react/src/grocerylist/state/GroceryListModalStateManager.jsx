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
                logger.error(`Unknown metadata from GroceryListToolbarStateManager ${metadata}`);
        }
    }
}