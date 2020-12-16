import React from 'react';
import PropTypes from 'prop-types';
import MessageInboxItem from './MessageInboxItem';
import inboxItemPropTypeShape from './propTypes/inboxItemPropTypeShape';

const MessageInbox = ({
    inboxItems, selectInboxItem,
}) => {
    const itemElements = inboxItems.map(item => (
        <li key={item.message.id}>
            <MessageInboxItem
                {...item}
                selectItem={() => selectInboxItem(item)}
            />
        </li>
    ));
    return (
        <div className="MessageInbox">
            <ol>
                {itemElements}
            </ol>
        </div>
    );
}

MessageInbox.propTypes = {
    inboxItems: PropTypes.arrayOf(PropTypes.shape(inboxItemPropTypeShape)),
    selectInboxItem: PropTypes.func.isRequired,
};

MessageInbox.defaultProps = {
    inboxItems: [],
};

export default MessageInbox;