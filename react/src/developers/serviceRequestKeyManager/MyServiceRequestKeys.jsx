import React from 'react';
import PropTypes from 'prop-types';
import serviceRequestKeyPropType from './serviceRequestKeyPropType';

const MyServiceRequestKeys = ({
    serviceRequestKeys,
}) => {
    return (
        <div className="MyServiceRequestKeys">
            Hello!
        </div>
    );
};

MyServiceRequestKeys.propTypes = {
    serviceRequestKeys: PropTypes.arrayOf(PropTypes.shape(serviceRequestKeyPropType)),
};

export default MyServiceRequestKeys;