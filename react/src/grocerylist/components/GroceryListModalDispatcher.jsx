import React, { useCallback } from 'react';
import PropTypes from 'prop-types';
import CreateGroceryListModal from './CreateGroceryListModal';

const GroceryListModalDispatcher = ({
    isOpen, modalId, close,
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
        default:
            return null;
    }
};

GroceryListModalDispatcher.propTypes = {
    isOpen: PropTypes.bool,
    modalId: PropTypes.string,
    close: PropTypes.func.isRequired,
};

GroceryListModalDispatcher.defaultProps = {
    isOpen: false,
    modalId: null,
};

export default GroceryListModalDispatcher;