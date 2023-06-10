import FormStateManager from 'tools/forms/state/FormStateManager';
import Request from 'tools/http/Request';
import { goTo } from 'common/js/urltools';

export default class CreateServiceRequestKeyFormStateManager extends FormStateManager {

    submitForm() {
        this.setState({ errors: null });

        const payload = this.packageFormData();
        Request.to('/developers/service-request-keys/app-api')
            .body(payload)
            .authenticated()
            .post()
            .then((response) => {
                goTo('/developers-service-request-keys');
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
                type: 'input',
                label: 'Expiration date',
                isRequired: false,
                validate: Boolean,
            },
        };
    }
}