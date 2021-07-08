import React from 'react';
import PropTypes from 'prop-types';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';
import FeatureFlagListItem from './FeatureFlagListItem';

const FeatureFlagList = ({
    featureFlags,
}) => {
    const listItems = featureFlags.map(item => (
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
    featureFlags: PropTypes.arrayOf(PropTypes.shape({
        featureFlag: PropTypes.shape(featureFlagPropTypeShape).isRequired,
        user: PropTypes.shape({
            id: PropTypes.number.isRequired,
        }),
    })),
};

FeatureFlagList.defaultProps = {
    featureFlags: [],
};

export default FeatureFlagList;