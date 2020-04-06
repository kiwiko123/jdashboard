import PropTypes from 'prop-types';
import tilePropType from './tilePropType';

export default PropTypes.shape({
    id: PropTypes.string.isRequired,
    availableTiles: PropTypes.arrayOf(tilePropType),
    playedTiles: PropTypes.arrayOf(tilePropType),
});