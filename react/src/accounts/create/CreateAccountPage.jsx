import React from 'react';
import { useCurrentUser } from '../../state/hooks';
import { goTo } from '../../common/js/urltools';
import DashboardPage from '../../dashboard/components/DashboardPage';
import CreateAccountFormWrapper from './components/CreateAccountFormWrapper';

export default function() {
    const currentUserData = useCurrentUser();
    if (currentUserData) {
        goTo('/home');
        return null;
    }

    return (
        <DashboardPage
            title="Create account"
            appId="createAccount"
        >
            <CreateAccountFormWrapper />
        </DashboardPage>
    );
}