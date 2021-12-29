import React, { useCallback } from 'react';
import { goTo } from 'common/js/urltools';
import inboxItemPropTypeShape from './propTypes/inboxItemPropTypeShape';

import './ChatroomInboxItem.css';

const ChatroomInboxItem = ({
    room, users,
}) => {
    const goToRoom = useCallback(() => {
        goTo(`/chatroom/room?r=${room.id}`);
    }, [room.id]);
    const userNames = users.map(user => user.displayName).join(', ');

    return (
        <div
            className="ChatroomInboxItem"
            onClick={goToRoom}
        >
            <div className="user-names">
                {userNames}
            </div>
        </div>
    );
};

ChatroomInboxItem.propTypes = {
    ...inboxItemPropTypeShape,
};

ChatroomInboxItem.defaultProps = {
};

export default ChatroomInboxItem;