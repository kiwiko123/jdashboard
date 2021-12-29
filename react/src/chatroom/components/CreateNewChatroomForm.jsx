import React from 'react';
import PropTypes from 'prop-types';
import InputFormField from 'common/forms/components/core/InputFormField';
import Tag from 'common/components/Tag';
import IconButton from 'common/components/IconButton';

import './CreateNewChatroomForm.css';

const CreateNewChatroomForm = ({
    fields, recipientUsernames, removeRecipientUsername, submitForm,
}) => {
    let addedUsernamesArea;
    if (recipientUsernames.length > 0) {
        const addedUsernameTags = recipientUsernames.map(username => (
            <Tag
                key={username}
                remove={() => removeRecipientUsername(username)}
            >
                {username}
            </Tag>
        ));
        addedUsernamesArea = (
            <div className="recipient-tags">
                {addedUsernameTags}
            </div>
        );
    }

    return (
        <div className="CreateNewChatroomForm">
            {addedUsernamesArea}
            <div className="fields">
                <InputFormField
                    {...fields.addRecipientUsername}
                    className="CreateNewChatroomForm"
                />
            </div>
            <div className="buttons">
                <IconButton
                    className="button-create-chatroom"
                    variant="success"
                    fontAwesomeClassName="fas fa-arrow-right"
                    disabled={!addedUsernamesArea}
                    onClick={submitForm}
                >
                    Create Chatroom
                </IconButton>
            </div>
        </div>
    );
};

CreateNewChatroomForm.propTypes = {
    recipientUsernames: PropTypes.arrayOf(PropTypes.string.isRequired),
    removeRecipientUsername: PropTypes.func.isRequired,
};

CreateNewChatroomForm.defaultProps = {
    recipientUsernames: [],
};

export default CreateNewChatroomForm;