import React from 'react';
import PropTypes from 'prop-types';
import StandardButton from 'ui/StandardButton';
import { goTo } from 'common/js/urltools';

const ServiceRequestKeysToolbar = ({
    serviceRequestKeys,
}) => {
    return (
        <div className="ServiceRequestKeysToolbar">
            <StandardButton
                className="new-service-request-key-button"
                variant="primary"
                fontAwesomeClassName="fas fa-key"
                onClick={() => goTo('/developers/service-request-key-manager/create')}
            >
                New key
            </StandardButton>
        </div>
    );
};

export default ServiceRequestKeysToolbar;