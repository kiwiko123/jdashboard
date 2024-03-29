import PropTypes from 'prop-types';

export default {
    label: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
    isRequired: PropTypes.bool,
    isValid: PropTypes.bool,
    value: PropTypes.any.isRequired,
};