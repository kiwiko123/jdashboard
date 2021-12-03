import { get, isEmpty, set } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import Request from 'common/js/Request';

const CREATE_NEW_ROOM_FROM_FORM_URL = '/chatroom/api/inbox/form/room';

export default class CreateNewChatroomFormStateManager extends StateTransmitter {
    constructor() {
        super();

        this.setState({
            recipientUsernames: [],
            fields: {
                addRecipientUsername: {
                    label: 'Recipient username',
                    name: 'recipientUsername',
                    isRequired: false,
                    isValid: true,
                    validate: value => !isEmpty(value),
                    value: null,
                    onChange: event => this.setField('addRecipientUsername', event.target.value),
                    onSubmit: () => this.addRecipientUsername(this.state.fields.addRecipientUsername.value),
                },
            },
        });
        this.registerMethod(this.removeRecipientUsername);
        this.registerMethod(this.submitForm);
    }

    addRecipientUsername(username) {
        const names = new Set(this.state.recipientUsernames);
        names.add(username);

        const { fields } = this.state;
        set(fields, 'addRecipientUsername.value', null);

        this.setState({
            recipientUsernames: Array.from(names),
            fields,
        });
    }

    removeRecipientUsername(username) {
        const names = new Set(this.state.recipientUsernames);
        names.delete(username);
        this.setState({ recipientUsernames: Array.from(names) });
    }

    submitForm() {
        const payload = {
            recipientUsernames: this.state.recipientUsernames,
        };
        Request.to(CREATE_NEW_ROOM_FROM_FORM_URL)
            .withAuthentication()
            .withBody(payload)
            .post()
            .then((response) => {
            });
    }

    setField(fieldName, fieldValue) {
        const { fields } = this.state;
        if (!fields[fieldName]) {
            return;
        }

        set(fields, [fieldName, 'value'], fieldValue);
        this.setState({ fields });
    }
}