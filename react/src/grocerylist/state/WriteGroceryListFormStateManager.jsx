import FormStateManager from 'tools/forms/state/FormStateManager';

export default class WriteGroceryListFormStateManager extends FormStateManager {

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

    packageFormData() {
        return {
            listName: this.state.fields.listName.value,
        };
    }
}