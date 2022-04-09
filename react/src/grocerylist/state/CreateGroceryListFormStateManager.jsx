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
        const formData = this.packageFormData();
        Request.to('/grocery-list/app-api/lists')
            .body(formData)
            .authenticated()
            .post()
            .then((response) => {

            });
    }
}