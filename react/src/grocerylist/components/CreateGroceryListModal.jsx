import React from 'react';
import PropTypes from 'prop-types';
import { useStateManager } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import TitleModal from 'common/components/Modal/TitleModal';
import CreateGroceryListFormStateManager from '../state/CreateGroceryListFormStateManager';
import CreateGroceryListForm from './CreateGroceryListForm';

const CreateGroceryListModal = ({
    isOpen, close,
}) => {
    const createGroceryListFormStateManager = useStateManager(() => new CreateGroceryListFormStateManager());

    return (
        <TitleModal
            className="CreateGroceryListModal"
            isOpen={isOpen}
            close={close}
            size="large"
            title="Create grocery list"
        >
            <ComponentStateManager
                stateManager={createGroceryListFormStateManager}
                component={CreateGroceryListForm}
            />
        </TitleModal>
    );
};

CreateGroceryListModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
};

CreateGroceryListModal.defaultProps = {
    isOpen: false,
};

export default CreateGroceryListModal;