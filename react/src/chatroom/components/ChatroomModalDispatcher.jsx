import React from 'react';
import PropTypes from 'prop-types';
import CreateNewChatroomModal from './CreateNewChatroomModal';

const ChatroomModalDispatcher = ({
    modalId, isOpen, close,
}) => {
    switch (modalId) {
        case 'openCreateRoomModal':
            return (
                <CreateNewChatroomModal
                    isOpen={isOpen}
                    close={close}
                />
            );
        default:
            return null;
    }
};

ChatroomModalDispatcher.propTypes = {
    modalId: PropTypes.string,
    isOpen: PropTypes.bool,
    close: PropTypes.func,
};

ChatroomModalDispatcher.defaultProps = {
    modalId: null,
    isOpen: false,
    close: null,
};

export default ChatroomModalDispatcher;