import PropTypes from 'prop-types';

export default PropTypes.shape({
    name: PropTypes.string.isRequired,

    // A function that takes in nothing and returns a Promise. The Promise should resolve to a boolean.
    condition: PropTypes.func.isRequired,
});