import React from 'react';
import PropTypes from 'prop-types';
import GroceryListFeedItem from './GroceryListFeedItem';

const GroceryListFeed = ({
    feedItems,
}) => {
    const feedItemElements = feedItems.map(item => (
        <GroceryListFeedItem
            {...item}
            key={item.groceryList.id}
        />
    ));
    return (
        <div className="GroceryListFeedItem">
            {feedItemElements}
        </div>
    );
};

GroceryListFeed.propTypes = {
    feedItems: PropTypes.arrayOf(PropTypes.shape(GroceryListFeedItem.propTypes)),
};

GroceryListFeed.defaultProps = {
    feedItems: [],
};

export default GroceryListFeed;