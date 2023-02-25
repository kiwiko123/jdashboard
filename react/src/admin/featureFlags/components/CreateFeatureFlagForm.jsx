import React from 'react';
import PropTypes from 'prop-types';
import SimpleForm from 'tools/forms/components/SimpleForm';

const CreateFeatureFlagForm = (props) => {
    return (
        <SimpleForm
            {...props}
            className="CreateFeatureFlagForm"
            theme="light"
        />
    );
};

CreateFeatureFlagForm.propTypes = {
    ...SimpleForm.propTypes,
};

CreateFeatureFlagForm.defaultProps = {
    ...SimpleForm.defaultProps,
};

export default CreateFeatureFlagForm;