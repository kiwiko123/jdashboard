import React from 'react';
import PropTypes from 'prop-types';
import IconButton from 'common/components/IconButton';

import './GroceryListPageToolbar.css';

const GroceryListPageToolbar = ({
    pressCreateGroceryListButton,
}) => {
    return (
        <div className="GroceryListPageToolbar">
            <IconButton
                className="create-grocery-list-button"
                variant="outline-light"
                fontAwesomeClassName="fas fa-plus"
                size="lg"
                block={true}
                onClick={pressCreateGroceryListButton}
            />
        </div>
    );
};

GroceryListPageToolbar.propTypes = {
    pressCreateGroceryListButton: PropTypes.func.isRequired,
};

export default GroceryListPageToolbar;