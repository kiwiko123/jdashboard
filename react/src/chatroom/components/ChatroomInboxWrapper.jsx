import React, { useCallback, useEffect } from 'react';
import { get } from 'lodash';
import ComponentStateWrapper from '../../state/components/ComponentStateWrapper';
import { useCurrentUserPromise, useStateObject } from '../../state/hooks';
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
    useEffect(() => {
        useCurrentUserPromise().then((data) => {
            setState({
                currentUserId: get(data, 'id'),
            });
        });

        if (!state.currentUserId) {
            return;
        }
        fetchPreviews(state.currentUserId).then((data) => {
            setState({
                inboxItems: data,
            });
        });
    }, [state.currentUserId]);
    const canResolve = useCallback(() => Boolean(state.currentUserId), [state.currentUserId]);

    return (
        <ComponentStateWrapper
            component={MessageInbox}
            data={state}
            canResolve={canResolve}
        />
    );
};

export default ChatroomInboxWrapper;