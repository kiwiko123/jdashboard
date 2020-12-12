import React, { useState } from 'react';
import PropTypes from 'prop-types';
import IconButton from '../../common/components/IconButton';
import Modal from '../../common/components/Modal/TitleModal';
import FormField from '../../common/forms/components/FormField';
import MessageInput from './MessageInput';

import './InboxToolbar.css';

const InboxToolbar = ({
    messageDraft, onInputTextChange, sendMessage, currentUserId,
}) => {
    const [shouldShowComposeModal, setShouldShowComposeModal] = useState(false);
    const [recipientUserId, setRecipientUserId] = useState(null);
    const sendMessageAction = () => {
        sendMessage();
        setShouldShowComposeModal(false);
    };

    return (
        <div className="InboxToolbar">
            <IconButton
                variant="light"
                fontAwesomeClassName="fas fa-plus-circle"
                onClick={() => setShouldShowComposeModal(true)}
            />
            <Modal
                className="compose-message-modal"
                isOpen={shouldShowComposeModal}
                size="large"
                close={() => setShouldShowComposeModal(false)}
                title="Compose message"
            >
                <FormField
                    className="recipient-field"
                    text={recipientUserId}
                    name="recipient"
                    label="To"
                    isRequired={true}
                    onChange={event => setRecipientUserId(Number(event.target.value))}
                />
                <MessageInput
                    messageDraft={messageDraft}
                    sendMessage={sendMessageAction}
                    onInputTextChange={onInputTextChange}
                />
            </Modal>
        </div>
    );
};

InboxToolbar.propTypes = {
    messageDraft: PropTypes.string,
    onInputTextChange: PropTypes.func.isRequired,
    sendMessage: PropTypes.func.isRequired,
};

InboxToolbar.defaultProps = {
    messageDraft: null,
};

export default InboxToolbar;