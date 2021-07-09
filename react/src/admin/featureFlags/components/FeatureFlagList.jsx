import React from 'react';
import PropTypes from 'prop-types';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';
import FeatureFlagListItem from './FeatureFlagListItem';

import './FeatureFlagList.css';

const FeatureFlagList = ({
    featureFlagListItems, openFeatureFlagForm,
}) => {
    const listItems = featureFlagListItems.map((item, index) => (
        <FeatureFlagListItem
            {...item.featureFlag}
            key={item.featureFlag.id}
            onClick={() => openFeatureFlagForm(index)}
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
    openFeatureFlagForm: PropTypes.func,
};

FeatureFlagList.defaultProps = {
    featureFlagListItems: [],
    openFeatureFlagForm: () => {},
};

export default FeatureFlagList;