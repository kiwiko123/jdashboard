import { get, set } from 'lodash';
import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';
import { goTo } from 'common/js/urltools';

export default class LoginFormStateManager extends StateManager {
    constructor() {
        super();
        this.setState({
            fields: {
                username: {
                    label: 'Username',
                    name: 'username',
                    isRequired: true,
                    isValid: null,
                    value: null,
                    onChange: event => this.setUsername(event.target.value),
                },
                password: {
                    label: 'Password',
                    name: 'password',
                    type: 'password',
                    isRequired: true,
                    isValid: null,
                    value: null,
                    onChange: event => this.setPassword(event.target.value),
                },
            },
            actions: {
                submitForm: this.submitForm.bind(this),
            },
            canSubmitForm: false,
        });
    }

    setUsername(username) {
        const { fields } = this.state;
        set(fields, 'username.isValid', Boolean(username));
        set(fields, 'username.value', username);

        this.setState({
            fields,
            canSubmitForm: this._isFormValid(),
        });
    }

    setPassword(password) {
        const { fields } = this.state;
        set(fields, 'password.isValid', Boolean(password));
        set(fields, 'password.value', password);

        this.setState({
            fields,
            canSubmitForm: this._isFormValid(),
        });
    }

    submitForm() {
        if (!this.state.canSubmitForm) {
            return;
        }

        const payload = {
            username: this.state.fields.username.value,
            password: this.state.fields.password.value,
        };

        Request.to('/user-auth/api/login')
            .body(payload)
            .authenticated()
            .post()
            .then((response) => {
                goTo('/home');
            })
            .finally(() => {
                this.setPassword(null);
            });
    }

    _isFormValid() {
        return Object.values(this.state.fields).every(field => field.isValid);
    }
}