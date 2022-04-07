import React from 'react';
import { get } from 'lodash';
import DashboardPage from 'dashboard/components/DashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import { getUrlParameters } from 'common/js/urltools';
import ChatroomRoomPermissionStateManager from './state/ChatroomRoomPermissionStateManager';
import ChatroomMessageFeedStateManager from './state/ChatroomMessageFeedStateManager';
import ChatroomMessageInputStateManager from './state/ChatroomMessageInputStateManager';
import ChatroomMessageFeed from './components/ChatroomMessageFeed';
import ChatroomMessageInput from './components/ChatroomMessageInput';

export default function() {
    const roomId = get(getUrlParameters(), 'r');
    if (!roomId) {
        throw new Error('R required');
    }

    // roomPermissionStateManager validates the current user's access and communicates with another state manager.
    // It is not bound directly to a component.
    //eslint-disable-next-line no-unused-vars
    const roomPermissionStateManager = useStateManager(() => new ChatroomRoomPermissionStateManager(roomId));
    const messageFeedStateManager = useStateManager(() => new ChatroomMessageFeedStateManager({ roomId }));
    const messageInputStateManager = useStateManager(() => new ChatroomMessageInputStateManager(roomId));

    return (
        <DashboardPage
            appId="chatroomRoom"
            title="Chatroom"
        >
            <ComponentStateManager
                broadcaster={messageFeedStateManager}
                component={ChatroomMessageFeed}
            />
            <ComponentStateManager
                broadcaster={messageInputStateManager}
                component={ChatroomMessageInput}
            />
        </DashboardPage>
    );
}