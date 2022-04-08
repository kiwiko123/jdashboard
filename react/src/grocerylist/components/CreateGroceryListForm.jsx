import React from 'react';
import PropTypes from 'prop-types';
import SimpleForm from 'tools/forms/components/SimpleForm';

const CreateGroceryListForm = ({
    fields, isFormValid,
}) => {
    return (
        <SimpleForm
            className="CreateGroceryListForm"
            fields={fields}
            isFormValid={isFormValid}
        />
    );
};

CreateGroceryListForm.propTypes = {
};

CreateGroceryListForm.defaultProps = {
};

export default CreateGroceryListForm;