import React, { useCallback } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';
import IconButton from '../../common/components/IconButton';
import FormInput from '../../common/forms/components/FormInput';

import './styles/MessageInput.css';

const MessageInput = ({
    className, sendMessage, onInputTextChange, messageDraft,
}) => {
    const divClassName = classnames('MessageInput', className);
    const onChange = useCallback(event => onInputTextChange(get(event, 'target.value')), [onInputTextChange]);

    return (
        <div className={divClassName}>
            <FormInput
                className="input"
                text={messageDraft}
                onChange={onChange}
                onSubmit={sendMessage}
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
    messageDraft: PropTypes.string,
};

MessageInput.defaultProps = {
    className: null,
    messageDraft: null,
};

export default MessageInput;