import React from 'react';
import PropTypes from 'prop-types';
import PazaakGameArea from './PazaakGameArea';
import playerPropType from './propTypes/playerPropType';

import './PazaakGame.css';

const PazaakGame = ({
    player, opponent,
}) => {
    return (
        <div className="PazaakGame">
            <PazaakGameArea
                className="player"
                playerName={player.id}
                handCards={player.handCards}
                placedCards={player.placedCards}
                score={player.score}
                playerStatus={player.playerStatus}
                showActionButtons={true}
            />
            <PazaakGameArea
                className="opponent"
                playerName={opponent.id}
                handCards={opponent.handCards}
                placedCards={opponent.placedCards}
                score={opponent.score}
                playerStatus={opponent.playerStatus}
            />
        </div>
    );
}

PazaakGame.propTypes = {
    player: PropTypes.shape(playerPropType).isRequired,
    opponent: PropTypes.shape(playerPropType).isRequired,
};

export default PazaakGame;