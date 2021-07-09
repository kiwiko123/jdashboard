import React, { useState } from 'react';
import PropTypes from 'prop-types';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';

import './FeatureFlagListItem.css';

const FeatureFlagListItem = ({
    id, name, status, userScope, userId, isRemoved, versions, onClick,
}) => {
    const flag = { id, name, status, userScope, userId, isRemoved, versions };

    return (
        <div
            className="FeatureFlagListItem"
            onClick={onClick}
        >
            <div className="name">
                {name}
            </div>
            <div className="fields">
                <div className="status">
                    <span className="label">
                        Status:
                    </span>
                    <span className="value">
                        {status}
                    </span>
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
};

FeatureFlagListItem.defaultProps = {
    onClick: () => {},
};

export default FeatureFlagListItem;