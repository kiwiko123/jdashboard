import PropTypes from 'prop-types';

export default {
    chatroomMessage: PropTypes.shape({
        id: PropTypes.number.isRequired,
        senderUserId: PropTypes.number.isRequired,
        message: PropTypes.string,
        messageStatus: PropTypes.string,
        sentDate: PropTypes.number,
    }).isRequired,
    senderDisplayName: PropTypes.string.isRequired,
    direction: PropTypes.oneOf(['inbound', 'outbound']).isRequired,
};