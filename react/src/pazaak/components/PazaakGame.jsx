import React from 'react';
import PropTypes from 'prop-types';
import PazaakGameArea from './PazaakGameArea';
import playerPropType from './propTypes/playerPropType';
import gameActionsPropType from './propTypes/gameActionsPropType';

import './PazaakGame.css';

const PazaakGame = ({
    player, opponent, currentPlayerId, winningPlayerId, actions,
}) => {
    return (
        <div className="PazaakGame">
            <PazaakGameArea
                className="player"
                player={player}
                showActionButtons={true}
                actions={actions}
            />
            <PazaakGameArea
                className="opponent"
                playerName={opponent.id}
                player={opponent}
                actions={actions}
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
};

PazaakGame.defaultProps = {
    currentPlayerId: null,
    winningPlayerId: null,
};

export default PazaakGame;