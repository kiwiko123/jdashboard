import React from 'react';
import PropTypes from 'prop-types';
import CreateFeatureFlagFormModal from './CreateFeatureFlagFormModal';
import EditFeatureFlagFormModal from './EditFeatureFlagFormModal';

const FeatureFlagModalDelegate = ({
    modal, isOpen, close,
}) => {
    if (!modal) {
        return null;
    }

    let content;
    switch (modal.id) {
        case 'createFeatureFlagForm':
            content = (
                <CreateFeatureFlagFormModal
                    isOpen={isOpen}
                    close={close}
                />
            );
            break;
        case 'editFeatureFlagForm':
            content = (
                <EditFeatureFlagFormModal
                    featureFlagId={modal.data.featureFlagId}
                    isOpen={isOpen}
                    close={close}
                />
            );
            break;
        default:
            content = null;
    }

    return content;
};

FeatureFlagModalDelegate.propTypes = {
    modal: PropTypes.shape({
        id: PropTypes.string.isRequired,
        data: PropTypes.object.isRequired,
    }),
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
};

FeatureFlagModalDelegate.defaultProps = {
    modal: null,
    isOpen: false,
};

export default FeatureFlagModalDelegate;