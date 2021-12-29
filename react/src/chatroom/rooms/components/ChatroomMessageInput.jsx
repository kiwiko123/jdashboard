import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import FormInput from 'common/forms/components/FormInput';
import IconButton from 'common/components/IconButton';

const ChatroomMessageInput = ({
  input, setInput, sendMessage,
}) => {
    const inputIsValid = Boolean(input);
    const divClassName = classnames('ChatroomMessageInput', {
        invalid: !inputIsValid,
    });

    return (
        <div
            className={divClassName}
        >
            <FormInput
                text={input}
                name="message-input"
                onChange={(event) => { setInput(event.target.value); }}
                onSubmit={inputIsValid && sendMessage}
            />
            <IconButton
                className="send-message-button"
                variant="link"
                fontAwesomeClassName="far fa-paper-plane"
                disabled={!inputIsValid}
            />
        </div>
    );
};

ChatroomMessageInput.propTypes = {
    input: PropTypes.string,
    setInput: PropTypes.func.isRequired,
    sendMessage: PropTypes.func.isRequired,
};

ChatroomMessageInput.defaultProps = {
    input: null,
};

export default ChatroomMessageInput;