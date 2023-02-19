import React from 'react';
import PropTypes from 'prop-types';
import SimpleForm from 'tools/forms/components/SimpleForm';

const EditFeatureFlagForm = (props) => {
    return (
        <SimpleForm
            {...props}
            className="EditFeatureFlagForm"
            theme="light"
        />
    );
};

EditFeatureFlagForm.propTypes = {
    ...SimpleForm.propTypes,
};

EditFeatureFlagForm.defaultProps = {
    ...SimpleForm.defaultProps,
};

export default EditFeatureFlagForm;