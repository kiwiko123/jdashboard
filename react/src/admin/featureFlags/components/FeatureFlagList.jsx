import React from 'react';
import PropTypes from 'prop-types';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';
import FeatureFlagListItem from './FeatureFlagListItem';

import './FeatureFlagList.css';

const FeatureFlagList = ({
    featureFlagListItems, openFeatureFlagForm, toggleFeatureFlagStatus, removeFeatureFlag,
}) => {
    const listItems = featureFlagListItems.map((item, index) => {
        const actions = {
            openEditModal: () => openFeatureFlagForm(index),
            toggleStatus: (event, value) => toggleFeatureFlagStatus(index, value),
            removeFlag: () => removeFeatureFlag(index),
        };
        return (
           <FeatureFlagListItem
               {...item.featureFlag}
               key={item.featureFlag.id}
               actions={actions}
               enableStatusToggle={true}
           />
       );
    });

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
    openFeatureFlagForm: PropTypes.func.isRequired,
    toggleFeatureFlagStatus: PropTypes.func.isRequired,
    removeFeatureFlag: PropTypes.func.isRequired,
};

FeatureFlagList.defaultProps = {
    featureFlagListItems: [],
};

export default FeatureFlagList;