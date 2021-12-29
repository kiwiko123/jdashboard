import PropTypes from 'prop-types';

export default {
    room: PropTypes.shape({
        id: PropTypes.number.isRequired,
    }).isRequired,
    roomUuid: PropTypes.string.isRequired,
    users: PropTypes.arrayOf(PropTypes.shape({
        userId: PropTypes.number.isRequired,
        displayName: PropTypes.string.isRequired,
    })).isRequired,
    lastUpdatedDate: PropTypes.number,
};