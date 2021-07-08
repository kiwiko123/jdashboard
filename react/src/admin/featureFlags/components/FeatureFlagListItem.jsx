import React from 'react';
import PropTypes from 'prop-types';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';

import './FeatureFlagListItem.css';

const FeatureFlagListItem = ({
    id, name, status, userScope,
}) => {
    return (
        <div className="FeatureFlagListItem">
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
};

FeatureFlagListItem.defaultProps = {
};

export default FeatureFlagListItem;