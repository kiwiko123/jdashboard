import React, { useCallback, useState } from 'react';
import ComponentStateWrapper from '../../../state/components/ComponentStateWrapper';
import Request from '../../../common/js/Request';
import { goTo } from '../../../common/js/urltools';
import CreateAccountForm from './CreateAccountForm';
import { CREATE_USER_URL } from '../../js/urls';

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
        setUsername,
        setPassword,
        createUser,
    };

    return (
        <ComponentStateWrapper data={data}>
            <CreateAccountForm />
        </ComponentStateWrapper>
    );
};

export default CreateAccountFormWrapper;