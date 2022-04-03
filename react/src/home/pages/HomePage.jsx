import React from 'react';
import JdashboardPage from '../../dashboard/components/JdashboardPage';
import HomeContent from '../components/HomeContent';
import HomeBroadcaster from '../state/HomeBroadcaster';
import DashboardNotificationsStateTransmitter from 'dashboard/notifications/state/DashboardNotificationsStateTransmitter';
import { useBroadcaster } from '../../state/hooks';
import ComponentStateManager from '../../state/components/ComponentStateManager';

const HomePage = () => {
    const homeBroadcaster = useBroadcaster(HomeBroadcaster);
    const broadcasterSubscribers = {
        notificationsBroadcaster: [homeBroadcaster],
    };
    const notificationsStateTransmitter = new DashboardNotificationsStateTransmitter('jdashboard-notifications');

    return (
        <JdashboardPage
            className="HomePage"
            title="Home"
            appId="home"
            broadcasterSubscribers={broadcasterSubscribers}
        >
            <ComponentStateManager
                broadcaster={notificationsStateTransmitter}
                component={HomeContent}
            />
        </JdashboardPage>
    );
};

export default HomePage;