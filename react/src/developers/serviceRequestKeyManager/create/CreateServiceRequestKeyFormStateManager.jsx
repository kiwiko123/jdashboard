import FormStateManager from 'tools/forms/state/FormStateManager';
import Request from 'tools/http/Request';
import { goTo } from 'common/js/urltools';

export default class CreateServiceRequestKeyFormStateManager extends FormStateManager {

    submitForm() {
        this.setState({ errors: null });

        const payload = {
            scope: this.state.fields.scope.value,
            serviceClientName: this.state.fields.serviceClientName.value,
            description: this.state.fields.description.value,
            expirationDate: new Date(this.state.fields.expirationDate.value).toISOString(),
        };

        Request.to('/developers/service-request-keys/app-api')
            .body(payload)
            .authenticated()
            .post()
            .then((response) => {
                goTo('/developers/service-request-key-manager');
            })
            .catch((error) => {
                this.setState({
                    errors: [{ message: 'An unexpected error occurred. Please try again.' }],
                });
            });
    }

    defaultFields() {
        return {
            scope: {
                name: 'scope',
                type: 'dropdownSelector',
                label: 'Scope',
                value: 'internal',
                isRequired: true,
                validate: Boolean,
                options: [
                    {
                        label: 'Internal',
                        value: 'internal',
                    },
                    {
                        label: 'External',
                        value: 'external',
                    },
                ],
            },
            serviceClientName: {
                name: 'serviceClientName',
                type: 'input',
                label: 'Client name',
                isRequired: true,
                validate: Boolean,
            },
            description: {
                name: 'description',
                type: 'input',
                label: 'Description',
                isRequired: true,
                validate: Boolean,
                validationFeedbackMessage: 'A date is required',
            },
            expirationDate: {
                name: 'expirationDate',
                type: 'datetime-local',
                label: 'Expiration date',
                isRequired: false,
                validate: date => new Date(date) >= new Date(),
            },
        };
    }
}