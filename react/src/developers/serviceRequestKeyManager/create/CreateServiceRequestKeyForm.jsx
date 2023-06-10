import React from 'react';
import PropTypes from 'prop-types';
import SimpleForm from 'tools/forms/components/SimpleForm';

const CreateServiceRequestKeyForm = (props) => {
    return (
        <SimpleForm
            {...props}
            className="CreateServiceRequestKeyForm"
            theme="dark"
        />
    );
};

CreateServiceRequestKeyForm.propTypes = {
    ...SimpleForm.propTypes,
};

CreateServiceRequestKeyForm.defaultProps = {
    ...SimpleForm.defaultProps,
};

export default CreateServiceRequestKeyForm;