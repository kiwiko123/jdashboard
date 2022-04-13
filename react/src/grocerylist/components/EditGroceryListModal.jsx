import React from 'react';
import PropTypes from 'prop-types';
import { useStateManager } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import TitleModal from 'common/components/Modal/TitleModal';
import EditGroceryListFormStateManager from '../state/EditGroceryListFormStateManager';
import CreateGroceryListForm from './CreateGroceryListForm';

const EditGroceryListModal = ({
    isOpen, close, groceryListId,
}) => {
    const editGroceryListFormStateManager = useStateManager(() => new EditGroceryListFormStateManager(groceryListId));

    return (
        <TitleModal
            className="CreateGroceryListModal"
            isOpen={isOpen}
            close={close}
            size="large"
            title="Edit grocery list"
        >
            <ComponentStateManager
                stateManager={editGroceryListFormStateManager}
                component={CreateGroceryListForm}
            />
        </TitleModal>
    );
};

EditGroceryListModal.propTypes = {
    isOpen: PropTypes.bool,
    close: PropTypes.func.isRequired,
    groceryListId: PropTypes.number.isRequired,
};

EditGroceryListModal.defaultProps = {
    isOpen: false,
};

export default EditGroceryListModal;