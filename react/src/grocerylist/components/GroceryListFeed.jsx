import React from 'react';
import PropTypes from 'prop-types';
import GroceryListFeedItem from './GroceryListFeedItem';

import './GroceryListFeed.css';

const GroceryListFeed = ({
    feedItems, title,
}) => {
    const feedItemElements = feedItems.map(item => (
        <GroceryListFeedItem
            {...item}
            key={item.groceryList.id}
        />
    ));
    return (
        <div className="GroceryListFeed">
            <h2 className="title">
                {title}
            </h2>
            <div className="feed">
                {feedItemElements}
            </div>
        </div>
    );
};

GroceryListFeed.propTypes = {
    feedItems: PropTypes.arrayOf(PropTypes.shape(GroceryListFeedItem.propTypes)),
    title: PropTypes.string,
};

GroceryListFeed.defaultProps = {
    feedItems: [],
    title: "My lists",
};

export default GroceryListFeed;