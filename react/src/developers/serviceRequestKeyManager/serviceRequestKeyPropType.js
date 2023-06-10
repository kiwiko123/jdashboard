import PropTypes from 'prop-types';

export default {
    id: PropTypes.number.isRequired,
    scope: PropTypes.string.isRequired,
    description: PropTypes.string,
    expirationDate: PropTypes.string.isRequired,
    requestToken: PropTypes.string.isRequired,
};