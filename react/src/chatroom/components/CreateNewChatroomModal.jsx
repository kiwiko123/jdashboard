import React from 'react';
import PropTypes from 'prop-types';
import { useStateManager } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import TitleModal from 'common/components/Modal/TitleModal';
import CreateNewChatroomForm from './CreateNewChatroomForm';
import CreateNewChatroomFormStateManager from '../state/CreateNewChatroomFormStateManager';

const CreateNewChatroomModal = ({
    isOpen, close,
}) => {
    const formStateManager = useStateManager(() => new CreateNewChatroomFormStateManager());
    return (
        <TitleModal
            className="CreateNewChatroomModal"
            isOpen={isOpen}
            close={close}
            title="Create Chatroom"
            size="small"
        >
            <ComponentStateManager
                stateManager={formStateManager}
                component={CreateNewChatroomForm}
            />
        </TitleModal>
    );
};

CreateNewChatroomModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
};

CreateNewChatroomModal.defaultProps = {
    isOpen: false,
};

export default CreateNewChatroomModal;