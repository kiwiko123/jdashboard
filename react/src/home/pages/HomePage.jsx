import React from 'react';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import HomeContent from '../components/HomeContent';
import DashboardNotificationsStateTransmitter from 'dashboard/notifications/state/DashboardNotificationsStateTransmitter';
import { useStateManager } from '../../state/hooks';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import SinglePageApp, { ControllerContext } from 'ui/SinglePageApp';

const HomePage = () => {
    const notificationsStateManager = useStateManager(() => new DashboardNotificationsStateTransmitter('jdashboard-notifications'));
    const Content = () => (
        <SinglePageApp>
            <HomeContent />
        </SinglePageApp>
    );

    return (
        <JdashboardPage
            className="HomePage"
            title="Home"
            appId="home"
        >
            <ComponentStateManager
                stateManager={notificationsStateManager}
                component={Content}
            />
        </JdashboardPage>
    );
};

export default HomePage;