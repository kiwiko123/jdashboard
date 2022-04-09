import StateManager from 'state/StateManager';

export default class GroceryListToolbarStateManager extends StateManager {
    constructor() {
        super();
        this.registerMethod(this.pressCreateGroceryListButton);
    }

    pressCreateGroceryListButton() {
        this.sendState('GroceryListModalStateManager', null, 'pressCreateGroceryListButton');
    }
}