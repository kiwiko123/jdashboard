import { mapValues } from 'lodash';
import StateManager from 'state/StateManager';
import logger from 'tools/monitoring/logging';
import * as FormOperations from '../util/formOperations';

//eslint-disable-next-line no-unused-vars
const FORM_FIELD_TEMPLATE = {
    name: 'exampleFieldName',
    type: 'input',
    label: 'Example',
    value: null,
    isRequired: true,
    validate: value => Boolean(value) && value.length > 0,
};

/**
 * Base state manager for creating simple forms. This state manager is meant to be linked with a SimpleForm component;
 * the state is packaged up in the exact shape that SimpleForm expects as props.
 */
export default class FormStateManager extends StateManager {
    constructor() {
        super();

        this.initForm();
        this.setState({
            actions: {
                submitForm: this.submitForm.bind(this),
                clearForm: this.clearForm.bind(this),
            },
        });
    }

    /**
     * Override to return the form's default fields. Refer to FORM_FIELD_TEMPLATE for an example of what makes up a field.
     * The returned object's keys should be field names, and values are the corresponding field definitions.
     *
     * @return {object} an object whose keys are field names and values are field definitions
     */
    defaultFields() {
        logger.warn(`No implementation of defaultFields in ${this.tag}`);
        return {};
    }

    /**
     * Override to define form submission logic. This function is called when the component's "Submit" button is pressed.
     */
    submitForm() {
        logger.warn(`No implementation of submitForm for ${this.tag}`);
    }

    /**
     * Add a new field to the form. Derived state managers should invoke this in their constructors to set up the form's necessary fields.
     */
    addField({ name, type, label, value, isRequired, validate }) {
        const { fields } = this.state;
        fields[name] = {
            name,
            type,
            label,
            value,
            isRequired,
            validate,
            isValid: FormOperations.isFieldValid({ value, isRequired, validate }),
            onChange: event => this.updateFieldValue(name, event.target.value),
            editCount: 0,
        };
        this.addState({ fields });
    }

    /**
     * Updates the value of a specific field. This is invoked when a field's value is updated.
     */
    updateFieldValue(fieldName, value) {
        const { fields } = this.state;
        const field = fields[fieldName];
        if (!field) {
            logger.error(`No field found named ${fieldName}`);
        }

        field.value = value;
        field.editCount = field.editCount + 1;
        const isValid = FormOperations.isFieldValid(field);
        field.isValid = isValid;

        this.setState({
            fields,
            isFormValid: FormOperations.isFormValid(fields),
        });
    }

    /**
     * Creates/restores the form state to its default values.
     */
    initForm() {
        this.addState({
            fields: {},
            isFormValid: false,
        });
        Object.entries(this.defaultFields())
            .forEach(([name, field]) => this.addField({ ...field, name }));
    }

    clearForm() {
        this.initForm();
        this.render();
    }

    packageFormData() {
        return mapValues(
            this.state.fields,
            field => ({
                name: field.name,
                value: field.value,
            }),
        );
    }
}