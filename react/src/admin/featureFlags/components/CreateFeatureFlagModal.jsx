import React from 'react';
import PropTypes from 'prop-types';
import TitleModal from '../../../common/components/Modal/TitleModal';
import CreateFeatureFlagForm from './CreateFeatureFlagForm';

const CreateFeatureFlagModal = ({
    isOpen, close,
}) => {
    return (
        <TitleModal
            className="create-feature-flag-modal"
            isOpen={isOpen}
            close={close}
            title="Create Feature Flag"
            size="large"
        >
            <CreateFeatureFlagForm />
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