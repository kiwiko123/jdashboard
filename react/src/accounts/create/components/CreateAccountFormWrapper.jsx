import React, { useCallback, useState } from 'react';
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
    const setUsernameFromEvent = useCallback(event => setTextFromEvent(event, setUsername));
    const setPasswordFromEvent = useCallback(event => setTextFromEvent(event, setPassword));
    const createUser = useCallback(() => {
        Request.to(CREATE_USER_URL)
            .withAuthentication()
            .withBody({ username, password })
            .post()
            .then(() => {
                goTo('/home');
            });
    }, [username, password]);

    return (
        <CreateAccountForm
            username={username}
            password={password}
            setUsername={setUsernameFromEvent}
            setPassword={setPasswordFromEvent}
            createUser={createUser}
        />
    );
};

export default CreateAccountFormWrapper;