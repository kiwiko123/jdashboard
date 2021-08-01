import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './PazaakGameHeader.css';

const PazaakGameHeader = ({
    className, playerName, playerStatus, score,
}) => {
    const divClassName = classnames('PazaakGameHeader', className);
    const turnIcon = playerStatus === 'ready' && (<i className="far fa-star turn-icon"/>);

    return (
        <div className={divClassName}>
            <h2 className="player-info">
                {playerName}
            </h2>
            <h2 className="score">
                {score}
            </h2>
            <div className="status">
                {turnIcon}
            </div>
        </div>
    );
};

PazaakGameHeader.propTypes = {
    className: PropTypes.string,
    playerName: PropTypes.string.isRequired,
    playerStatus: PropTypes.string.isRequired,
    score: PropTypes.number,
};

PazaakGameHeader.defaultProps = {
    className: null,
    score: 0,
};

export default PazaakGameHeader;