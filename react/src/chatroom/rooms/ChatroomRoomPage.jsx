import React from 'react';
import { get } from 'lodash';
import DashboardPage from 'dashboard/components/DashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import { getUrlParameters } from 'common/js/urltools';
import ChatroomMessageFeedStateManager from './state/ChatroomMessageFeedStateManager';
import ChatroomMessageFeed from './components/ChatroomMessageFeed';

export default function() {
    const roomId = get(getUrlParameters(), 'r');
    if (!roomId) {
        throw new Error('R required');
    }

    const messageFeedStateManager = useStateManager(() => new ChatroomMessageFeedStateManager({ roomId }));

    return (
        <DashboardPage
            appId="chatroomRoom"
            title="Chatroom"
        >
            <ComponentStateManager
                broadcaster={messageFeedStateManager}
                component={ChatroomMessageFeed}
            />
        </DashboardPage>
    );
}