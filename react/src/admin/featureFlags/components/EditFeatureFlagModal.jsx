import React, { useState } from 'react';
import PropTypes from 'prop-types';
import TitleModal from 'common/components/Modal/TitleModal';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateTransmitter } from 'state/hooks';
import CreateFeatureFlagForm from './CreateFeatureFlagForm';
import EditFeatureFlagFormStateTransmitter from '../state/EditFeatureFlagFormStateTransmitter';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';

const EditFeatureFlagModal = ({
    isOpen, close, featureFlag,
}) => {
    const editFeatureFlagFormStateTransmitter = useStateTransmitter(EditFeatureFlagFormStateTransmitter, featureFlag);

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
                broadcaster={editFeatureFlagFormStateTransmitter}
            />
        </TitleModal>
    );
};

EditFeatureFlagModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
    featureFlag: PropTypes.shape(featureFlagPropTypeShape).isRequired,
};

EditFeatureFlagModal.defaultProps = {
    isOpen: false,
};

export default EditFeatureFlagModal;