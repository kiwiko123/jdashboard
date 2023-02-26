import React from 'react';
import PropTypes from 'prop-types';
import LoadingIndicator from 'ui/LoadingIndicator';
import ToggleSwitch from 'common/buttons/components/ToggleSwitch';
import IconButton from 'common/components/IconButton';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';

import './FeatureFlagListItem.css';

const FeatureFlagListItem = ({
    id, name, status, value, userScope, userId, isRemoved, versions, actions, disabled, isLoading,
}) => {
    const userIdField = userScope === 'individual' && (
        <div className="userId">
            <span className="label">
                User ID:
            </span>
            <span className="value">
                {userId}
            </span>
        </div>
    );

    return (
        <div className="FeatureFlagListItem">
            <div className="toolbar">
                <div className="name">
                    {name}
                </div>
                <div className="indicators">
                    {isLoading && <LoadingIndicator />}
                    <IconButton
                        className="edit-button"
                        fontAwesomeClassName="fas fa-edit"
                        onClick={actions.openEditModal}
                        size="lg"
                        disabled={disabled}
                    />
                    <IconButton
                        className="delete-button"
                        fontAwesomeClassName="fas fa-trash-alt"
                        onClick={actions.removeFlag}
                        size="lg"
                        disabled={disabled}
                    />
                </div>
            </div>
            <div className="fields">
                <div className="status">
                    <span className="label">
                        Status
                    </span>
                    <ToggleSwitch
                        className="status-switch value"
                        isSelected={status === 'enabled'}
                        onToggle={actions.toggleStatus}
                        disabled={disabled}
                    />
                </div>
                <div className="flag-value">
                    <span className="label">
                        Value:
                    </span>
                    <span className="value">
                        {value}
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
                {userIdField}
            </div>
        </div>
    );
}

FeatureFlagListItem.propTypes = {
    ...featureFlagPropTypeShape,
    actions: PropTypes.shape({
        openEditModal: PropTypes.func.isRequired,
        toggleStatus: PropTypes.func.isRequired,
        removeFlag: PropTypes.func.isRequired,
    }),
    disabled: PropTypes.bool,
    isLoading: PropTypes.bool,
};

FeatureFlagListItem.defaultProps = {
    disabled: false,
    isLoading: false,
};

export default FeatureFlagListItem;