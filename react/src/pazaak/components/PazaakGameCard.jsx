import React from 'react';
import PropTypes from 'prop-types';

import './PazaakGameCard.css';

const PazaakGameCard = ({
    modifier, onClick,
}) => {
    return (
        <div
            className="PazaakGameCard"
            onClick={onClick}
        >
            <div className="modifier">
                {modifier}
            </div>
        </div>
    );
}

PazaakGameCard.propTypes = {
    modifier: PropTypes.number.isRequired,
    onClick: PropTypes.func,
};

PazaakGameCard.defaultProps = {
    onClick: null,
};

export default PazaakGameCard;