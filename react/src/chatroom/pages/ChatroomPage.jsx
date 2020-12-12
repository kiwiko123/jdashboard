import React from 'react';
import { get } from 'lodash';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import { useBroadcaster, useCurrentUser, usePushService } from '../../state/hooks';
import ChatroomBroadcaster from '../state/ChatroomBroadcaster';
import ChatroomInputBroadcaster from '../state/ChatroomInputBroadcaster';
import ChatroomInboxBroadcaster from '../state/ChatroomInboxBroadcaster';
import ChatroomPushBroadcaster from '../state/ChatroomPushBroadcaster';
import ChatroomContents from '../components/ChatroomContents';
import MessageInput from '../components/MessageInput';
import MessageInbox from '../components/MessageInbox';
import InboxToolbar from '../components/InboxToolbar';

import '../components/styles/ChatroomPage.css';

const ChatroomPage = () => {
    const pushBroadcaster = useBroadcaster(ChatroomPushBroadcaster);
    const chatroomBroadcaster = useBroadcaster(ChatroomBroadcaster);
    const inputBroadcaster = useBroadcaster(ChatroomInputBroadcaster);
    const inboxBroadcaster = useBroadcaster(ChatroomInboxBroadcaster);

    chatroomBroadcaster.listenTo(pushBroadcaster);
    chatroomBroadcaster.listenTo(inboxBroadcaster);
    inputBroadcaster.listenTo(inboxBroadcaster);

    const broadcasterSubscribers = {
        userDataBroadcaster: [chatroomBroadcaster, pushBroadcaster, inboxBroadcaster],
    };

    return (
        <DashboardPage
            className="ChatroomPage"
            title="Chatroom"
            appId="chatroom"
            broadcasterSubscribers={broadcasterSubscribers}
        >
            <div className="left-pane">
                <ComponentStateManager
                    broadcaster={inputBroadcaster}
                    component={InboxToolbar}
                    canResolve={({ currentUserId }) => Boolean(currentUserId)}
                />
                <ComponentStateManager
                    broadcaster={inboxBroadcaster}
                    component={MessageInbox}
                />
            </div>
            <div className="right-pane">
                <ComponentStateManager
                    broadcaster={chatroomBroadcaster}
                    component={ChatroomContents}
                />
                <ComponentStateManager broadcaster={inputBroadcaster}>
                    <MessageInput />
                </ComponentStateManager>
            </div>
        </DashboardPage>
    );
};

export default ChatroomPage;