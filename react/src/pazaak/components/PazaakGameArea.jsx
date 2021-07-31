import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { sortBy } from 'lodash';
import PazaakGameHeader from './PazaakGameHeader';
import PazaakGameCards from './PazaakGameCards';
import PazaakActionButtons from './PazaakActionButtons';

import './PazaakGameArea.css';

const PazaakGameArea = ({
    placedCards, handCards, playerName, className, showActionButtons, score, playerStatus,
}) => {
    const divClassName = classnames('PazaakGameArea', className);
    const headerData = {
        playerName,
        playerStatus,
        score,
    };
    const actionButtonsData = {
        endTurn: {
            onClick: () => {},
            disabled: playerStatus !== 'ready',
        },
        stand: {
            onClick: () => {},
            disabled: playerStatus !== 'ready',
        },
        forfeit: {
            onClick: () => {},
            disabled: false,
        },
    };

    const actionButtons = showActionButtons && (
        <PazaakActionButtons {...actionButtonsData} />
    );

    return (
        <div className={divClassName}>
            <PazaakGameHeader
                {...headerData}
                className={playerName}
            />
            <hr className="divider" />
            <PazaakGameCards
                className="placed"
                cards={sortBy(placedCards, card => card.modifier)}
            />
            <hr className="divider" />
            <PazaakGameCards
                className="hand"
                cards={sortBy(handCards, card => card.modifier)}
            />
            <hr className="divider" />
            {actionButtons}
        </div>
    );
}

PazaakGameArea.propTypes = {
    playerName: PropTypes.string.isRequired,
    playerStatus: PropTypes.string.isRequired,
    score: PropTypes.number,
    placedCards: PropTypes.arrayOf(PropTypes.shape({
        modifier: PropTypes.number.isRequired,
    })),
    handCards: PropTypes.arrayOf(PropTypes.shape({
       modifier: PropTypes.number.isRequired,
   })),
   className: PropTypes.string,
   showActionButtons: PropTypes.bool,
};

PazaakGameArea.defaultProps = {
    placedCards: [],
    handCards: [],
    className: null,
    showActionButtons: false,
    score: 0,
};

export default PazaakGameArea;