import React from 'react';
import JdashboardPage from 'dashboard/components/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import { authenticatedUser } from 'tools/dashboard/conditions';
import CreateGroceryListFormStateManager from './state/CreateGroceryListFormStateManager';
import CreateGroceryListForm from './components/CreateGroceryListForm';

export default function() {
    const createGroceryListFormStateManager = useStateManager(() => new CreateGroceryListFormStateManager());

    return (
        <JdashboardPage
            appId="groceryList"
            title="Grocery List"
            requiredConditions={[authenticatedUser()]}
        >
            <ComponentStateManager
                stateManager={createGroceryListFormStateManager}
                component={CreateGroceryListForm}
            />
        </JdashboardPage>
    );
}