import React, { useState } from 'react';
import PropTypes from 'prop-types';
import ToggleSwitch from 'common/buttons/components/ToggleSwitch';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';

import './FeatureFlagListItem.css';

const FeatureFlagListItem = ({
    id, name, status, userScope, userId, isRemoved, versions, onClick, toggleStatus, index,
}) => {
    const flag = { id, name, status, userScope, userId, isRemoved, versions };

    return (
        <div className="FeatureFlagListItem">
            <div className="name">
                {name}
            </div>
            <div className="fields">
                <div className="status">
                    <span className="label">
                        Status
                    </span>
                    <ToggleSwitch
                        className="status-switch"
                        isSelected={status === 'enabled'}
                        onToggle={(event, value) => toggleStatus(index, value)}
                    />
                </div>
                <div className="user-scope">
                    <span className="label">
                        User scope:
                    </span>
                    <span className="value">
                        {userScope}
                    </span>
                </div>
            </div>
        </div>
    );
}

FeatureFlagListItem.propTypes = {
    ...featureFlagPropTypeShape,
    onClick: PropTypes.func,
    toggleStatus: PropTypes.func,
    index: PropTypes.number,
};

FeatureFlagListItem.defaultProps = {
    onClick: () => {},
    toggleStatus: () => {},
    index: null,
};

export default FeatureFlagListItem;