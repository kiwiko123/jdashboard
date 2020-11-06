import PropTypes from 'prop-types';

export default PropTypes.shape({
    id: PropTypes.number,
    message: PropTypes.string,
    messageType: PropTypes.string,
    messageStatus: PropTypes.string,
    senderUserId: PropTypes.number,
    recipientUserId: PropTypes.number,
    sentDate: PropTypes.string,
});