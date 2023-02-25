import React from 'react';
import PropTypes from 'prop-types';
import TitleModal from 'common/components/Modal/TitleModal';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import CreateFeatureFlagFormStateManager from '../state/CreateFeatureFlagFormStateManager';
import CreateFeatureFlagForm from './CreateFeatureFlagForm';

const CreateFeatureFlagFormModal = ({
    featureFlagId, isOpen, close,
}) => {
    const formManager = useStateManager(
        () => new CreateFeatureFlagFormStateManager({
            onSuccessfulCreation: close,
        }),
    );

    return (
        <TitleModal
            className="create-feature-flag-modal"
            isOpen={isOpen}
            close={close}
            title="Create Feature Flag"
            size="large"
        >
            <ComponentStateManager
                component={CreateFeatureFlagForm}
                stateManager={formManager}
            />
        </TitleModal>
    );
};

CreateFeatureFlagFormModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
};

CreateFeatureFlagFormModal.defaultProps = {
    isOpen: false,
};

export default CreateFeatureFlagFormModal;