import React, { useCallback } from 'react';
import { get } from 'lodash';
import ComponentStateWrapper from '../../state/components/ComponentStateWrapper';
import { useCurrentUser, useStateObject } from '../../state/hooks';
import Request from '../../common/js/Request';
import MessageInbox from '../components/MessageInbox';

function fetchPreviews(userId) {
    const previewUrl = `/messages/api/get/users/${userId}/previews`;
    return Request.to(previewUrl)
        .withAuthentication()
        .get();
}

const ChatroomInboxWrapper = () => {
    const [state, setState] = useStateObject({
        inboxItems: [],
        selectInboxItem: () => {}, // TODO
        currentUserId: null,
    });
    useCurrentUser().then((currentUser) => {
        const currentUserId = get(currentUser, 'id');
        fetchPreviews(currentUserId).then((data) => {
            setState({
                currentUserId,
                inboxItems: data,
            });
        });
    });
    const canResolve = useCallback(() => Boolean(state.currentUserId), [state.currentUserId]);

    return (
        <ComponentStateWrapper
            data={state}
            canResolve={canResolve}
        >
            <MessageInbox />
        </ComponentStateWrapper>
    );
};

export default ChatroomInboxWrapper;