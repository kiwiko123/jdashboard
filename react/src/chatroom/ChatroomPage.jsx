import React from 'react';
import DashboardPage from 'dashboard/components/DashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';

export default function() {
    return (
        <DashboardPage
            appId="chatroom"
            title="Chatroom"
        >
            Test
        </DashboardPage>
    );
}