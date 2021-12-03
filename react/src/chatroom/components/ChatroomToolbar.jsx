import React, { useState } from 'react';
import PropTypes from 'prop-types';
import IconButton from 'common/components/IconButton';

const ChatroomToolbar = ({
    actions,
}) => {
    return (
        <div className="ChatroomToolbar">
            <IconButton
                className="button-create-room"
                variant="outline-light"
                fontAwesomeClassName="fas fa-plus"
                size="lg"
                onClick={actions.openCreateRoomModal}
            />
        </div>
    );
};

ChatroomToolbar.propTypes = {
    actions: PropTypes.shape({
        openCreateRoomModal: PropTypes.func.isRequired,
    }).isRequired,
};

ChatroomToolbar.defaultProps = {
};

export default ChatroomToolbar;