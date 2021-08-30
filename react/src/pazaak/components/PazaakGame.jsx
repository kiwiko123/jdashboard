import React from 'react';
import PropTypes from 'prop-types';
import PazaakGameArea from './PazaakGameArea';
import playerPropType from './propTypes/playerPropType';
import gameActionsPropType from './propTypes/gameActionsPropType';

import './PazaakGame.css';

const PazaakGame = ({
    player, opponent, currentPlayerId, winningPlayerId, actions, errorMessage,
}) => {
    const winnerArea = winningPlayerId && (
        <div className="winner-alert">
            {winningPlayerId}
        </div>
    );
    return (
        <div className="PazaakGame">
            {winnerArea}
            <PazaakGameArea
                className="player"
                player={player}
                showActionButtons={!winningPlayerId}
                actions={actions}
                errorMessage={currentPlayerId === player.id ? errorMessage : null}
            />
            <PazaakGameArea
                className="opponent"
                playerName={opponent.id}
                player={opponent}
                actions={actions}
                errorMessage={currentPlayerId === opponent.id ? errorMessage : null}
            />
        </div>
    );
}

PazaakGame.propTypes = {
    player: PropTypes.shape(playerPropType).isRequired,
    opponent: PropTypes.shape(playerPropType).isRequired,
    currentPlayerId: PropTypes.string,
    winningPlayerId: PropTypes.string,
    actions: PropTypes.shape(gameActionsPropType).isRequired,
    errorMessage: PropTypes.string,
};

PazaakGame.defaultProps = {
    currentPlayerId: null,
    winningPlayerId: null,
    errorMessage: null,
};

export default PazaakGame;