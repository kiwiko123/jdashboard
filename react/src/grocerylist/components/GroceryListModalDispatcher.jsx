import React, { useCallback } from 'react';
import PropTypes from 'prop-types';
import CreateGroceryListModal from './CreateGroceryListModal';
import EditGroceryListModal from './EditGroceryListModal';

const GroceryListModalDispatcher = ({
    isOpen, modalId, close, data,
}) => {
    const closeModal = useCallback(() => close(modalId), [modalId, close]);

    switch (modalId) {
        case 'createGroceryList':
            return (
                <CreateGroceryListModal
                    isOpen={isOpen}
                    close={closeModal}
                />
            );
        case 'editGroceryList':
            return (
                <EditGroceryListModal
                    isOpen={isOpen}
                    close={closeModal}
                    groceryListId={data.groceryListId}
                />
            );
        default:
            return null;
    }
};

GroceryListModalDispatcher.propTypes = {
    isOpen: PropTypes.bool,
    modalId: PropTypes.string,
    close: PropTypes.func.isRequired,
    data: PropTypes.object,
};

GroceryListModalDispatcher.defaultProps = {
    isOpen: false,
    modalId: null,
    data: {},
};

export default GroceryListModalDispatcher;