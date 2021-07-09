import React from 'react';
import PropTypes from 'prop-types';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';
import FeatureFlagListItem from './FeatureFlagListItem';

import './FeatureFlagList.css';

const FeatureFlagList = ({
    featureFlagListItems,
}) => {
    const listItems = featureFlagListItems.map(item => (
        <FeatureFlagListItem
            {...item.featureFlag}
            key={item.featureFlag.id}
        />
    ));

    return (
        <div className="FeatureFlagList">
            {listItems}
        </div>
    );
};

FeatureFlagList.propTypes = {
    featureFlagListItems: PropTypes.arrayOf(PropTypes.shape({
        featureFlag: PropTypes.shape(featureFlagPropTypeShape).isRequired,
        user: PropTypes.shape({
            id: PropTypes.number.isRequired,
        }),
    })),
};

FeatureFlagList.defaultProps = {
    featureFlagListItems: [],
};

export default FeatureFlagList;