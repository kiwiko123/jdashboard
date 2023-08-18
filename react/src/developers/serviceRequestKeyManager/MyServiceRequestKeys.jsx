import React from 'react';
import PropTypes from 'prop-types';
import Table from 'ui/Table';
import SensitiveDisplayContent from 'ui/SensitiveDisplayContent';
import serviceRequestKeyPropType from './serviceRequestKeyPropType';

import './MyServiceRequestKeys.css';

const MyServiceRequestKeys = ({
    serviceRequestKeys,
}) => {
    const headers = [
        { label: 'Scope' },
        { label: 'Service client name' },
        { label: 'Description' },
        { label: 'Expiration date' },
        { label: 'Request token' },
    ];

    const tableRows = serviceRequestKeys.map(serviceRequestKey => ({
        columns: [
            {
                name: 'scope',
                content: serviceRequestKey.scope,
            },
            {
                name: 'serviceClientName',
                content: serviceRequestKey.serviceClientName,
            },
            {
                name: 'description',
                content: serviceRequestKey.description,
            },
            {
                name: 'expirationDate',
                content: serviceRequestKey.expirationDate,
            },
            {
                name: 'requestToken',
                content: <SensitiveDisplayContent startHidden={true}>{serviceRequestKey.requestToken}</SensitiveDisplayContent>,
            },
        ],
        name: serviceRequestKey.id,
    }));

    return (
        <div className="MyServiceRequestKeys">
            <h2 className="heading">
                My keys
            </h2>
            <Table
                className="service-request-keys-list"
                headers={headers}
                rows={tableRows}
                style="grid"
            />
        </div>
    );
};

MyServiceRequestKeys.propTypes = {
    serviceRequestKeys: PropTypes.arrayOf(PropTypes.shape(serviceRequestKeyPropType)),
};

export default MyServiceRequestKeys;