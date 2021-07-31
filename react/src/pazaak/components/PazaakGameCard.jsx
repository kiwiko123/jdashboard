import React from 'react';
import PropTypes from 'prop-types';

import './PazaakGameCard.css';

const PazaakGameCard = ({
    modifier,
}) => {
    return (
        <div className="PazaakGameCard">
            <div className="modifier">
                {modifier}
            </div>
        </div>
    );
}

PazaakGameCard.propTypes = {
    modifier: PropTypes.number.isRequired,
};

export default PazaakGameCard;