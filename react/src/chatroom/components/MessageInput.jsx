import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';

const MessageInput = ({
    className, onSend, onTextChange,
}) => {
    const divClassName = classnames('MessageInput', className);
    const onChange = event => onTextChange(get(event, 'target.value'));

    return (
        <div className={divClassName}>
            <input
                className="input"
                type="text"
                onChange={onChange}
            />
        </div>
    );
};

MessageInput.propTypes = {
    onSend: PropTypes.func,
    onTextChange: PropTypes.func,
    className: PropTypes.string,
};

MessageInput.defaultProps = {
    onSend: () => {},
    onTextChange: () => {},
    className: null,
};

export default MessageInput;