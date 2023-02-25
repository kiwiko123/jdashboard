import React from 'react';
import PropTypes from 'prop-types';
import StandardButton from 'ui/StandardButton';
import FormInput from 'common/forms/components/FormInput';

import './FeatureFlagPageToolbar.css';

const FeatureFlagPageToolbar = ({
    searchInput, onSearchInputChange, openCreateFeatureFlagModal,
}) => {
    return (
        <div className="FeatureFlagPageToolbar">
            <div className="search">
                <FormInput
                    className="feature-flag-search"
                    name="feature-flag-search"
                    placeholder="Search..."
                    text={searchInput}
                    onChange={onSearchInputChange}
                />
            </div>
            <div className="quick-buttons">
                <StandardButton
                    variant="outline-light"
                    fontAwesomeClassName="fas fa-plus"
                    className="toolbar-button"
                    onClick={openCreateFeatureFlagModal}
                >
                    Create feature flag
                </StandardButton>
            </div>
        </div>
    );
};

FeatureFlagPageToolbar.propTypes = {
    searchInput: PropTypes.string,
    onSearchInputChange: PropTypes.func.isRequired,
    openCreateFeatureFlagModal: PropTypes.func.isRequired,
};

export default FeatureFlagPageToolbar;