import React from 'react';
import JdashboardPage from 'dashboard/components/DashboardPage';
// import JdashboardPage from 'tools/dashboard/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import ChatroomInboxStateManager from './state/ChatroomInboxStateManager';
import ChatroomModalStateManager from './state/ChatroomModalStateManager';
import ChatroomToolbarStateManager from './state/ChatroomToolbarStateManager';
import ChatroomInbox from './components/ChatroomInbox';
import ChatroomModalDispatcher from './components/ChatroomModalDispatcher';
import ChatroomToolbar from './components/ChatroomToolbar';

export default function() {
    const inboxStateManager = useStateManager(() => new ChatroomInboxStateManager());
    const modalStateManager = useStateManager(() => new ChatroomModalStateManager());
    const toolbarStateManager = useStateManager(() => new ChatroomToolbarStateManager());

    return (
        <JdashboardPage
            appId="chatroom"
            title="Chatroom"
        >
            <ComponentStateManager
                stateManager={modalStateManager}
                component={ChatroomModalDispatcher}
            />
            <ComponentStateManager
                stateManager={toolbarStateManager}
                component={ChatroomToolbar}
            />
            <ComponentStateManager
                stateManager={inboxStateManager}
                component={ChatroomInbox}
            />
        </JdashboardPage>
    );
}