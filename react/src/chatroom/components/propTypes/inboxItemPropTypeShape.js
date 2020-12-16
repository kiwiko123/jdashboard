import PropTypes from 'prop-types';
import messagePropType from './messagePropType';

export default {
    users: PropTypes.arrayOf(PropTypes.shape({
        userId: PropTypes.number.isRequired,
        userName: PropTypes.string.isRequired,
    })),
    message: messagePropType,
};