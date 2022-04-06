import React from 'react';
import JdashboardPage from '../../dashboard/components/JdashboardPage';
import HomeContent from '../components/HomeContent';
import DashboardNotificationsStateTransmitter from 'dashboard/notifications/state/DashboardNotificationsStateTransmitter';
import { useStateManager } from '../../state/hooks';
import ComponentStateManager from '../../state/components/ComponentStateManager';

const HomePage = () => {
    const notificationsStateManager = useStateManager(() => new DashboardNotificationsStateTransmitter('jdashboard-notifications'));

    return (
        <JdashboardPage
            className="HomePage"
            title="Home"
            appId="home"
        >
            <ComponentStateManager
                stateManager={notificationsStateManager}
                component={HomeContent}
            />
        </JdashboardPage>
    );
};

export default HomePage;