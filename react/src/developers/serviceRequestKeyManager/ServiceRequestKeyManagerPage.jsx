import React from 'react';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import { authenticatedUser } from 'tools/dashboard/conditions';
import ServiceRequestKeysToolbar from './ServiceRequestKeysToolbar';
import MyServiceRequestKeysStateManager from './MyServiceRequestKeysStateManager';
import MyServiceRequestKeys from './MyServiceRequestKeys';

export default function() {
    const myServiceRequestKeysStateManager = useStateManager(() => new MyServiceRequestKeysStateManager());

    return (
        <JdashboardPage
            className="ServiceRequestKeyManagerPage"
            appId="serviceRequestKeyManager"
            title="Service Request Key Manager"
            requiredConditions={[authenticatedUser]}
        >
            <ServiceRequestKeysToolbar />
            <ComponentStateManager
                stateManager={myServiceRequestKeysStateManager}
                component={MyServiceRequestKeys}
            />
        </JdashboardPage>
    );
}