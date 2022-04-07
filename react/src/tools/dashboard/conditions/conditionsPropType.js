import PropTypes from 'prop-types';

export default PropTypes.shape({
    name: PropTypes.string.isRequired,
    condition: PropTypes.instanceOf(Promise).isRequired,
});