import React, { useState } from 'react';
import PropTypes from 'prop-types';
import TitleModal from 'common/components/Modal/TitleModal';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateTransmitter } from 'state/hooks';
import CreateFeatureFlagForm from './CreateFeatureFlagForm';
import CreateFeatureFlagFormStateTransmitter from '../state/CreateFeatureFlagFormStateTransmitter';

const CreateFeatureFlagModal = ({
    isOpen, close,
}) => {
    const createFeatureFlagFormStateTransmitter = useStateTransmitter(CreateFeatureFlagFormStateTransmitter;

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
                broadcaster={createFeatureFlagFormStateTransmitter}
            />
        </TitleModal>
    );
};

CreateFeatureFlagModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
};

CreateFeatureFlagModal.defaultProps = {
    isOpen: false,
};

export default CreateFeatureFlagModal;