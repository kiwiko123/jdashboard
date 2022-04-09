import FormStateManager from 'tools/forms/state/FormStateManager';
import Request from 'tools/http/Request';

export default class CreateGroceryListFormStateManager extends FormStateManager {

    defaultFields() {
        return {
            listName: {
                name: 'listName',
                type: 'input',
                label: 'List name',
                isRequired: true,
                validate: name => Boolean(name) && name.length > 0,
            },
        };
    }

    submitForm() {
        const payload = {
            listName: this.state.fields.listName.value,
        };
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