import React from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import HomeContent from '../components/HomeContent';
import HomeBroadcaster from '../state/HomeBroadcaster';
import { useBroadcaster } from '../../state/hooks';
import ComponentStateManager from '../../state/components/ComponentStateManager';

const HomePage = () => {
    const homeBroadcaster = useBroadcaster(HomeBroadcaster);
    const broadcasterSubscribers = {
        notificationsBroadcaster: [homeBroadcaster],
    };

    return (
        <DashboardPage
            className="HomePage"
            title="Home"
            appId="home"
            broadcasterSubscribers={broadcasterSubscribers}
        >
            <ComponentStateManager
                broadcaster={homeBroadcaster}
                component={HomeContent}
            />
        </DashboardPage>
    );
};

export default HomePage;