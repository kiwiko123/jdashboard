import StateManager from 'state/StateManager';
import logger from 'tools/monitoring/logging';
import * as FormOperations from '../util/formOperations';

export default class FormStateManager extends StateManager {
    constructor() {
        super();

        this.addState({
            fields: {},
        });

        Object.entries(this.defaultFields())
            .forEach(([name, field]) => this.addField({ ...field, name }));

        this.setState({
            isFormValid: FormOperations.isFormValid(this.state.fields),
        });
    }

    defaultFields() {
        logger.warn(`No implementation of defaultFields in ${this.tag}`);
        return {};
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
}