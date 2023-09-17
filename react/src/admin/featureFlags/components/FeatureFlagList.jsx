import React from 'react';
import PropTypes from 'prop-types';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';
import FeatureFlagListItem from './FeatureFlagListItem';

import './FeatureFlagList.css';

const FeatureFlagList = ({
    featureFlagListItems, editFeatureFlagForm, toggleFeatureFlagStatusForMe, toggleFeatureFlagStatusForPublic, removeFeatureFlag,
}) => {
    const listItems = featureFlagListItems.map((item, index) => {
        const actions = {
            openEditModal: () => editFeatureFlagForm(index),
            toggleStatusForMe: () => toggleFeatureFlagStatusForMe(index),
            toggleStatusForPublic: () => toggleFeatureFlagStatusForPublic(index),
            removeFlag: () => removeFeatureFlag(index),
        };
        return (
           <FeatureFlagListItem
               {...item}
               key={item.id}
               actions={actions}
               disabled={item.disabled}
               isLoading={item.isLoading}
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
    featureFlagListItems: PropTypes.arrayOf(PropTypes.shape(FeatureFlagListItem.propTypes)),
    editFeatureFlagForm: PropTypes.func.isRequired,
    toggleFeatureFlagStatusForMe: PropTypes.func.isRequired,
    toggleFeatureFlagStatusForPublic: PropTypes.func.isRequired,
    removeFeatureFlag: PropTypes.func.isRequired,
};

FeatureFlagList.defaultProps = {
    featureFlagListItems: [],
};

export default FeatureFlagList;