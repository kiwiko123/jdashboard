import FormStateManager from 'tools/forms/state/FormStateManager';

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
}