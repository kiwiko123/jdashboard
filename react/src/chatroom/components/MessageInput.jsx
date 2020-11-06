import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';
import IconButton from '../../common/components/IconButton';

import './styles/MessageInput.css';

const MessageInput = ({
    className, sendMessage, onInputTextChange, recipientUserId, messageDraft,
}) => {
    if (!recipientUserId) {
        return null;
    }

    const divClassName = classnames('MessageInput', className);
    const onChange = event => onInputTextChange(get(event, 'target.value'));

    return (
        <div className={divClassName}>
            <input
                className="input"
                type="text"
                onChange={onChange}
            />
            <IconButton
                className="send-button"
                fontAwesomeClassName="far fa-paper-plane"
                onClick={sendMessage}
                disabled={!messageDraft}
            />
        </div>
    );
};

MessageInput.propTypes = {
    sendMessage: PropTypes.func.isRequired,
    onInputTextChange: PropTypes.func.isRequired,
    className: PropTypes.string,
    recipientUserId: PropTypes.number,
    messageDraft: PropTypes.string,
};

MessageInput.defaultProps = {
    className: null,
    recipientUserId: null,
    messageDraft: null,
};

export default MessageInput;