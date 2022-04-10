import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import IconButton from 'common/components/IconButton';

import './GroceryListFeedItem.css';

function getHoverActionElement(groceryList, action) {
    let iconClassName;
    switch (action.type) {
        case 'edit':
            iconClassName = 'fas fa-pen';
            break;
        case 'remove':
            iconClassName = 'fas fa-trash';
            break;
    }

    return (
        <IconButton
            key={action.type}
            className={`hover-action-icon ${action.type}`}
            fontAwesomeClassName={iconClassName}
            onClick={() => action.onClick(groceryList.id)}
        />
    );
}

const GroceryListFeedItem = ({
    groceryList, itemCount, hoverActions,
}) => {
    const [isHovering, setIsHovering] = useState(false);
    const hover = useCallback(() => setIsHovering(true), []);
    const stopHover = useCallback(() => setIsHovering(false), []);

    const divClassName = classnames('GroceryListFeedItem', {
         hovering: isHovering,
     });
    let hoverActionsOverlay;
    if (hoverActions && isHovering) {
        const hoverActionElements = hoverActions.map(action => getHoverActionElement(groceryList, action));
        hoverActionsOverlay = (
            <div className="hover-overlay">
                {hoverActionElements}
            </div>
        );
    }
    const itemCountCaption = itemCount === 1 ? '1 item' : `${itemCount} items`;

    return (
        <div
            className={divClassName}
            onMouseEnter={hover}
            onMouseLeave={stopHover}
        >
            {hoverActionsOverlay}
            <div className="list-name">
                {groceryList.name}
            </div>
            <hr className="divider" />
            <div className="info">
                <div className="item-count">
                    {itemCountCaption}
                </div>
                <div className="action-date">
                    {groceryList.displayDate}
                </div>
            </div>
        </div>
    );
};

GroceryListFeedItem.propTypes = {
    groceryList: PropTypes.shape({
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
        displayDate: PropTypes.string.isRequired,
    }).isRequired,
    itemCount: PropTypes.number,
    hoverActions: PropTypes.arrayOf(PropTypes.shape({
        type: PropTypes.string.isRequired,
        onClick: PropTypes.func.isRequired,
    })),
};

GroceryListFeedItem.defaultProps = {
    itemCount: 0,
    hoverActions: null,
};

export default GroceryListFeedItem;