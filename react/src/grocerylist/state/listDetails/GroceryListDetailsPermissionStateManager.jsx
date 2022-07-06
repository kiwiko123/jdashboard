import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';
import { goTo } from 'common/js/urltools';

export default class GroceryListDetailsPermissionStateManager extends StateManager {
    constructor(groceryListId) {
        super();
        Request.to(`/grocery-list/app-api/lists/${groceryListId}/permissions`)
            .authenticated()
            .get()
            .then((response) => {
                const { canAccess } = response;
                this.setState({ canAccess });
                if (!canAccess) {
                    goTo('/grocerylist');
                }
            });
    }
}