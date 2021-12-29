import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import PazaakGameCard from './PazaakGameCard';

import './PazaakGameCards.css';

const PazaakGameCards = ({
    cards, className, actions,
}) => {
    const divClassName = classnames('PazaakGameCards', className);
    const cardElements = cards.map(card => (
        <PazaakGameCard
            key={card.id}
            modifier={card.modifier}
            onClick={actions.selectCard ? () => actions.selectCard(card.id) : null}
        />
    ));

    return (
        <div className={divClassName}>
            {cardElements}
        </div>
    );
}

PazaakGameCards.propTypes = {
    cards: PropTypes.arrayOf(PropTypes.shape({
        modifier: PropTypes.number.isRequired,
    })),
    className: PropTypes.string,
    actions: PropTypes.shape({
        selectCard: PropTypes.func.isRequired,
    }),
};

PazaakGameCards.defaultProps = {
    cards: [],
    actions: {},
};

export default PazaakGameCards;