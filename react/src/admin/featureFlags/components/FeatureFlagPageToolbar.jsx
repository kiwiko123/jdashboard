import React from 'react';
import PropTypes from 'prop-types';
import IconButton from '../../../common/components/IconButton';

import './FeatureFlagPageToolbar.css';

const FeatureFlagPageToolbar = ({
    pressCreateButton,
}) => {
    return (
        <div className="FeatureFlagPageToolbar">
            <IconButton
                className="create-feature-flag-button"
                variant="outline-light"
                fontAwesomeClassName="fas fa-plus"
                size="lg"
                block={true}
                onClick={pressCreateButton}
            />
        </div>
    );
};

FeatureFlagPageToolbar.propTypes = {
    pressCreateButton: PropTypes.func.isRequired,
};

export default FeatureFlagPageToolbar;