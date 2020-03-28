import PropTypes from 'prop-types';

export default PropTypes.shape({
    playerId: PropTypes.string.isRequired,
    character: PropTypes.string.isRequired,
});