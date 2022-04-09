import React from 'react';
import PropTypes from 'prop-types';

const GroceryListFeedItem = ({
    groceryList, itemCount,
}) => {
    return (
        <div className="GroceryListFeedItem">
            <span className="list-name">
                {groceryList.name}
            </span>
        </div>
    );
};

GroceryListFeedItem.propTypes = {
    groceryList: PropTypes.shape({
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
    }).isRequired,
    itemCount: PropTypes.number,
};

GroceryListFeedItem.defaultProps = {
    itemCount: 0,
};

export default GroceryListFeedItem;