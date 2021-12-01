import React from 'react';
import PropTypes from 'prop-types';
import IconButton from 'common/components/IconButton';

import './PazaakActionButtons.css';

const PazaakActionButtons = ({
    endTurn, stand, forfeit, newGame,
}) => {
    return (
        <div className="PazaakActionButtons">
            <IconButton
                {...endTurn}
                className="end-turn-button"
                variant="success"
                fontAwesomeClassName="fas fa-play"
            >
                End turn
            </IconButton>
            <IconButton
                {...stand}
                className="stand-button"
                variant="warning"
                fontAwesomeClassName="fas fa-arrow-up"
            >
                Stand
            </IconButton>
            <IconButton
                {...forfeit}
                className="forfeit-button"
                variant="danger"
                fontAwesomeClassName="fas fa-door-open"
            >
                Forfeit
            </IconButton>
            <IconButton
                {...newGame}
                className="new-game-button"
                variant="info"
                fontAwesomeClassName="fas fa-redo"
            >
                New game
            </IconButton>
        </div>
    );
};

PazaakActionButtons.propTypes = {
    endTurn: PropTypes.shape({
        disabled: PropTypes.bool,
        onClick: PropTypes.func.isRequired,
    }).isRequired,
    stand: PropTypes.shape({
       disabled: PropTypes.bool,
       onClick: PropTypes.func.isRequired,
   }).isRequired,
   forfeit: PropTypes.shape({
       disabled: PropTypes.bool,
       onClick: PropTypes.func.isRequired,
   }).isRequired,
   newGame: PropTypes.shape({
       disabled: PropTypes.bool,
       onClick: PropTypes.func.isRequired,
   }).isRequired,
};

PazaakActionButtons.defaultProps = {
};

export default PazaakActionButtons;