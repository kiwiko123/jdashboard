import React, { useState } from 'react';
import PropTypes from 'prop-types';
import TitleModal from 'common/components/Modal/TitleModal';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateTransmitter } from 'state/hooks';
import FeatureFlagForm from './FeatureFlagForm';
import CreateFeatureFlagFormStateTransmitter from '../state/CreateFeatureFlagFormStateTransmitter';
import EditFeatureFlagFormStateTransmitter from '../state/EditFeatureFlagFormStateTransmitter';
import featureFlagPropTypeShape from './propTypes/featureFlagPropTypeShape';

const FeatureFlagModal = ({
    isOpen, close, title, createFormTransmitter, formType,
}) => {
    const createFormManager = useStateTransmitter(CreateFeatureFlagFormStateTransmitter);
    const editFormManager = useStateTransmitter(EditFeatureFlagFormStateTransmitter);
    const formManager = formType === 'create' ? createFormManager : editFormManager;

    return (
        <TitleModal
            className="create-feature-flag-modal"
            isOpen={isOpen}
            close={close}
            title={title}
            size="large"
        >
            <ComponentStateManager
                component={FeatureFlagForm}
                broadcaster={formManager}
            />
        </TitleModal>
    );
};

FeatureFlagModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
    createFormTransmitter: PropTypes.func.isRequired,
    title: PropTypes.string.isRequired,
    formType: PropTypes.oneOf(['create', 'edit']).isRequired,
};

FeatureFlagModal.defaultProps = {
    isOpen: false,
};

export default FeatureFlagModal;