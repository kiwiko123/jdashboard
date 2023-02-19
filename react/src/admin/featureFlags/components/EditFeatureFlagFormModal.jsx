import React from 'react';
import PropTypes from 'prop-types';
import TitleModal from 'common/components/Modal/TitleModal';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import EditFeatureFlagFormStateManager from '../state/EditFeatureFlagFormStateManager';
import EditFeatureFlagForm from './EditFeatureFlagForm';

const EditFeatureFlagFormModal = ({
    featureFlagId, isOpen, close,
}) => {
    const formManager = useStateManager(
        () => new EditFeatureFlagFormStateManager(
            featureFlagId,
            { onSuccessfulEdit: close },
        ),
    );

    return (
        <TitleModal
            className="edit-feature-flag-modal"
            isOpen={isOpen}
            close={close}
            title="Edit Feature Flag"
            size="large"
        >
            <ComponentStateManager
                component={EditFeatureFlagForm}
                stateManager={formManager}
            />
        </TitleModal>
    );
};

EditFeatureFlagFormModal.propTypes = {
    featureFlagId: PropTypes.number.isRequired,
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
};

EditFeatureFlagFormModal.defaultProps = {
    isOpen: false,
};

export default EditFeatureFlagFormModal;