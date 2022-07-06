import WriteGroceryListFormStateManager from './WriteGroceryListFormStateManager';
import Request from 'tools/http/Request';
import logger from 'tools/monitoring/logging';

export default class EditGroceryListFormStateManager extends WriteGroceryListFormStateManager {

    constructor(groceryListId) {
        super();
        this.groceryListId = groceryListId;

        Request.to(`/grocery-lists/public-api/${groceryListId}`)
            .authenticated()
            .get()
            .then((response) => {
                if (!response) {
                    logger.error(`No grocery list found with ID ${groceryListId}`);
                    throw new Error('No grocery list found');
                }
                const { fields } = this.state;
                fields.listName.value = response.name;
                this.setState({ fields });
            });
    }

    submitForm() {
        const payload = {
            groceryList: {
                name: this.state.fields.listName.value,
            },
        };
        Request.to(`/grocery-list/app-api/lists/${this.groceryListId}`)
            .body(payload)
            .authenticated()
            .patch()
            .then((response) => {
                this.sendState('GroceryListFeedStateManager', null, 'refresh');
                this.sendState('GroceryListModalStateManager', null, 'close');
            });
    }
}