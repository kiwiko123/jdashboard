import { mapValues } from 'lodash';
import StateManager from 'state/StateManager';
import logger from 'tools/monitoring/logging';
import * as FormOperations from '../util/formOperations';

export default class FormStateManager extends StateManager {
    constructor() {
        super();

        this.initForm();
        this.setState({
            isFormValid: FormOperations.isFormValid(this.state.fields),
            actions: {
                submitForm: this.submitForm.bind(this),
                clearForm: this.clearForm.bind(this),
            },
        });
    }

    defaultFields() {
        logger.warn(`No implementation of defaultFields in ${this.tag}`);
        return {};
    }

    submitForm() {
        logger.warn(`No implementation of submitForm for ${this.tag}`);
    }

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

    initForm() {
        this.addState({ fields: {} });
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