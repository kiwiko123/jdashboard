import React from 'react';
import PropTypes from 'prop-types';
import LoadingIndicator from 'ui/LoadingIndicator';
import ToggleSwitch from 'common/buttons/components/ToggleSwitch';
import IconButton from 'common/components/IconButton';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';

import './FeatureFlagListItem.css';

const FeatureFlagListItem = ({
    id, name, isOnForMe, isOnForPublic, createdDate, lastUpdatedDate, rules, actions, disabled, isLoading,
}) => {
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
                <div className="status for-me">
                    <ToggleSwitch
                        className="status-switch value"
                        isSelected={isOnForMe}
                        onToggle={actions.toggleStatusForMe}
                        disabled={disabled}
                    />
                    <span className="label">
                        For me
                    </span>
                </div>
                <div className="status for-everyone">
                    <ToggleSwitch
                        className="status-switch value"
                        isSelected={isOnForPublic}
                        onToggle={actions.toggleStatusForPublic}
                        disabled={disabled}
                    />
                    <span className="label">
                        For everyone
                    </span>
                </div>
            </div>
            <div className="dates">
                <div className="last-updated-date">
                    {`Last updated on ${lastUpdatedDate}`}
                </div>
                <div className="created-date">
                    {`Created on ${createdDate}`}
                </div>
            </div>
        </div>
    );
}

FeatureFlagListItem.propTypes = {
    ...featureFlagPropTypeShape,
    actions: PropTypes.shape({
        openEditModal: PropTypes.func.isRequired,
        toggleStatusForMe: PropTypes.func.isRequired,
        toggleStatusForPublic: PropTypes.func.isRequired,
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