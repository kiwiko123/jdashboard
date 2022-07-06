import WriteGroceryListFormStateManager from './WriteGroceryListFormStateManager';
import Request from 'tools/http/Request';

export default class CreateGroceryListFormStateManager extends WriteGroceryListFormStateManager {

    submitForm() {
        const payload = this.packageFormData();
        Request.to('/grocery-list/app-api/lists')
            .body(payload)
            .authenticated()
            .post()
            .then((response) => {
                this.sendState('GroceryListFeedStateManager', null, 'refresh');
                this.sendState('GroceryListModalStateManager', null, 'close');
            });
    }
}