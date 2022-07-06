import React from 'react';
import SimpleForm from 'tools/forms/components/SimpleForm';

const CreateGroceryListForm = (props) => {
    return (
        <SimpleForm
            {...props}
            className="CreateGroceryListForm"
        />
    );
};

CreateGroceryListForm.propTypes = {
    ...SimpleForm.propTypes,
};

CreateGroceryListForm.defaultProps = {
    ...SimpleForm.defaultProps,
};

export default CreateGroceryListForm;