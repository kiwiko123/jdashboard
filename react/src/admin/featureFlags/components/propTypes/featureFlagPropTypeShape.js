import PropTypes from 'prop-types';

export default {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    status: PropTypes.string.isRequired,
    userScope: PropTypes.string.isRequired,
    value: PropTypes.string,
    userId: PropTypes.number,
    isRemoved: PropTypes.bool.isRequired,
    versions: PropTypes.array,
};