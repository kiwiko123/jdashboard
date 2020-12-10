import React, { useCallback, useState } from 'react';
import ComponentStateWrapper from '../../../state/components/ComponentStateWrapper';
import Request from '../../../common/js/Request';
import { goTo } from '../../../common/js/urltools';
import CreateAccountForm from './CreateAccountForm';
import { CREATE_USER_URL } from '../../js/urls';

function setTextFromEvent(event, setText) {
    setText(event.target.value);
}

const CreateAccountFormWrapper = () => {
    const [username, setUsername] = useState(null);
    const [password, setPassword] = useState(null);
    const createUser = useCallback(() => {
        Request.to(CREATE_USER_URL)
            .withAuthentication()
            .withBody({ username, password })
            .post()
            .then(() => {
                goTo('/home');
            });
    }, [username, password]);
    const data = {
        username,
        password,
        setUsername: event => setTextFromEvent(event, setUsername),
        setPassword: event => setTextFromEvent(event, setPassword),
        createUser,
    };

    return (
        <ComponentStateWrapper
            component={CreateAccountForm}
            data={data}
        />
    );
};

export default CreateAccountFormWrapper;