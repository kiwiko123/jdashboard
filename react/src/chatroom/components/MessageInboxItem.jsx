import React from 'react';
import PropTypes from 'prop-types';
import inboxItemPropTypeShape from './propTypes/inboxItemPropTypeShape';

import './styles/MessageInboxItem.css';

const MessageInboxItem = ({
    users, message, selectItem,
}) => {
    const userNames = users.map(user => user.userName)
        .join(', ');

    return (
        <div
            role="row"
            className="MessageInboxItem"
            onClick={selectItem}
        >
            <div className="names">
                {userNames}
            </div>
            <div className="preview">
                {message.message}
            </div>
        </div>
    );
}

MessageInboxItem.propTypes = {
    ...inboxItemPropTypeShape,
    selectItem: PropTypes.func.isRequired,
};

MessageInboxItem.defaultProps = {
    users: [],
};

export default MessageInboxItem;