import React from 'react';
import PropTypes from 'prop-types';

import './FeatureFlagPageNavigationItem.css';

const FeatureFlagPageNavigationItem = ({
    icon, label,
}) => (
    <div className="FeatureFlagPageNavigationItem">
        <div className="icon">
            {icon}
        </div>
        <span className="label">{label}</span>
    </div>
);

FeatureFlagPageNavigationItem.propTypes = {
    icon: PropTypes.node.isRequired,
    label: PropTypes.string.isRequired,
};

export default FeatureFlagPageNavigationItem;