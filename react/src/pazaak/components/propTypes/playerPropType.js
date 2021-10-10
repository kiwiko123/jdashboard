import PropTypes from 'prop-types';
import cardPropType from './cardPropType';

export default {
    id: PropTypes.string.isRequired,
    placedCards: PropTypes.arrayOf(PropTypes.shape(cardPropType)),
    handCards: PropTypes.arrayOf(PropTypes.shape(cardPropType)),
    score: PropTypes.number,
    gameStatus: PropTypes.oneOf(['ready', 'end_turn', 'stand', null]), // PazaakPlayerStatuses.java
};