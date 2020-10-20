import React from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import { useBroadcaster } from '../../state/hooks/broadcasterHooks';
import ChatroomBroadcaster from '../state/ChatroomBroadcaster';
import ChatroomContents from '../components/ChatroomContents';

const ChatroomPage = () => {
    const chatroomBroadcaster = useBroadcaster(ChatroomBroadcaster);
    const broadcasterSubscribers = {
        userDataBroadcaster: [chatroomBroadcaster],
    };

    return (
        <DashboardPage
            title="Chatroom"
            appId="chatroom"
            broadcasterSubscribers={broadcasterSubscribers}
        >
            <ComponentStateManager
                broadcaster={chatroomBroadcaster}
            >
                <ChatroomContents />
            </ComponentStateManager>
        </DashboardPage>
    );
};

export default ChatroomPage;