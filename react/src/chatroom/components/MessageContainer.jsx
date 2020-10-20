import React from 'react';
import PropTypes from 'prop-types';
import messagePropType from './propTypes/messagePropType';

const MessageContainer = ({
}) => {
    return (
        <div className="MessageContainer">
        </div>
    );
}

MessageContainer.propTypes = {
    messages: PropTypes.arrayOf(messagePropType),
};

MessageContainer.defaultProps = {
    messages: [],
};

export default MessageContainer;