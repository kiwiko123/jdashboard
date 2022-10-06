import React from 'react';
import PropTypes from 'prop-types';
import TitleModal from 'common/components/Modal/TitleModal';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import FeatureFlagForm from './FeatureFlagForm';
import FeatureFlagFormStateTransmitter from '../state/FeatureFlagFormStateTransmitter';
import logger from 'common/js/logging';

const FeatureFlagModal = ({
    isOpen, close, title, formType,
}) => {
    const formManager = useStateManager(() => new FeatureFlagFormStateTransmitter());
    logger.debug(`Form manager ${formManager.id}`);

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
                stateManager={formManager}
            />
        </TitleModal>
    );
};

FeatureFlagModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
    title: PropTypes.string.isRequired,
    formType: PropTypes.oneOf(['create', 'edit']).isRequired,
};

FeatureFlagModal.defaultProps = {
    isOpen: false,
};

export default FeatureFlagModal;