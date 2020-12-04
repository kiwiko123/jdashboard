import React from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import { useBroadcaster } from '../../state/hooks/broadcasterHooks';
import { usePushService } from '../../state/hooks';
import ChatroomBroadcaster from '../state/ChatroomBroadcaster';
import ChatroomInputBroadcaster from '../state/ChatroomInputBroadcaster';
import ChatroomInboxBroadcaster from '../state/ChatroomInboxBroadcaster';
import ChatroomContents from '../components/ChatroomContents';
import MessageInput from '../components/MessageInput';
import MessageInbox from '../components/MessageInbox';

import '../components/styles/ChatroomPage.css';

const ChatroomPage = () => {
    const chatroomBroadcaster = useBroadcaster(ChatroomBroadcaster);
    const inputBroadcaster = useBroadcaster(ChatroomInputBroadcaster);
    const inboxBroadcaster = useBroadcaster(ChatroomInboxBroadcaster);

    inboxBroadcaster.listenTo(chatroomBroadcaster);
    inputBroadcaster.listenTo(chatroomBroadcaster);

    const broadcasterSubscribers = {
        userDataBroadcaster: [chatroomBroadcaster],
    };

    const { pushToServer } = usePushService('chatroom', 1, {
        receivePush: () => console.log('Client received push'),
    });

    return (
        <DashboardPage
            className="ChatroomPage"
            title="Chatroom"
            appId="chatroom"
            broadcasterSubscribers={broadcasterSubscribers}
        >
            <div className="left-pane">
                <ComponentStateManager broadcaster={inboxBroadcaster}>
                    <MessageInbox />
                </ComponentStateManager>
            </div>
            <div className="right-pane">
                <ComponentStateManager broadcaster={chatroomBroadcaster}>
                    <ChatroomContents />
                </ComponentStateManager>
                <ComponentStateManager broadcaster={inputBroadcaster}>
                    <MessageInput />
                </ComponentStateManager>
            </div>
        </DashboardPage>
    );
};

export default ChatroomPage;