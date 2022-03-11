import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { sortBy } from 'lodash';
import { goTo } from 'common/js/urltools';
import PazaakGameHeader from './PazaakGameHeader';
import PazaakGameCards from './PazaakGameCards';
import PazaakActionButtons from './PazaakActionButtons';
import playerPropType from './propTypes/playerPropType';
import gameActionsPropType from './propTypes/gameActionsPropType';

import './PazaakGameArea.css';

const PazaakGameArea = ({
    player, className, showActionButtons, actions, errorMessage,
}) => {
    const divClassName = classnames('PazaakGameArea', className);
    const headerData = {
        playerName: player.id,
        playerStatus: player.playerStatus,
        score: player.score,
        errorMessage,
    };
    const actionButtonsData = {
        endTurn: {
            onClick: () => actions.endTurn(player.id),
            disabled: player.playerStatus !== 'ready',
        },
        stand: {
            onClick: () => {},
            disabled: player.playerStatus !== 'ready',
        },
        forfeit: {
            onClick: () => {},
            disabled: false,
        },
        newGame: {
            onClick: () => goTo('/pazaak/play'),
            disabled: false,
        },
    };

    const handCardActions = {
        selectCard: actions.selectHandCard,
    };

    const actionButtons = showActionButtons && (
        <PazaakActionButtons {...actionButtonsData} />
    );

    return (
        <div className={divClassName}>
            <PazaakGameHeader
                {...headerData}
                className={player.id}
            />
            <hr className="divider" />
            <PazaakGameCards
                className="placed"
                cards={player.placedCards}
            />
            <hr className="divider" />
            <PazaakGameCards
                className="hand"
                cards={sortBy(player.handCards, card => card.modifier)}
                actions={handCardActions}
            />
            <hr className="divider" />
            {actionButtons}
        </div>
    );
}

PazaakGameArea.propTypes = {
   player: PropTypes.shape(playerPropType).isRequired,
   className: PropTypes.string,
   showActionButtons: PropTypes.bool,
   actions: PropTypes.shape(gameActionsPropType).isRequired,
   errorMessage: PropTypes.string,
};

PazaakGameArea.defaultProps = {
    className: null,
    showActionButtons: false,
    errorMessage: null,
};

export default PazaakGameArea;