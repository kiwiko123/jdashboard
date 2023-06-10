import React from 'react';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import { authenticatedUser } from 'tools/dashboard/conditions';
import CreateServiceRequestKeyFormStateManager from './CreateServiceRequestKeyFormStateManager';
import CreateServiceRequestKeyForm from './CreateServiceRequestKeyForm';

export default function() {
    const createServiceRequestKeyFormStateManager = useStateManager(() => new CreateServiceRequestKeyFormStateManager());

    return (
        <JdashboardPage
            className="CreateServiceRequestKeyPage"
            appId="createServiceRequestKey"
            title="Create Service Request Key"
            requiredConditions={[authenticatedUser]}
        >
            <ComponentStateManager
                stateManager={createServiceRequestKeyFormStateManager}
                component={CreateServiceRequestKeyForm}
            />
        </JdashboardPage>
    );
}