import React from 'react';
import { useParams } from 'react-router-dom';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import { authenticatedUser } from 'tools/dashboard/conditions';
import GroceryListDetailsPermissionStateManager from './state/listDetails/GroceryListDetailsPermissionStateManager';
import GroceryListDetailsPageBody from './components/listDetails/GroceryListDetailsPageBody';

export default function() {
    const { listId } = useParams();
    const listDetailsPermissionStateManager = useStateManager(() => new GroceryListDetailsPermissionStateManager(listId));

    return (
        <JdashboardPage
            className="GroceryListDetailsPage"
            appId="groceryListDetails"
            title="Grocery List"
            requiredConditions={[authenticatedUser]}
        >
            <ComponentStateManager
                stateManager={listDetailsPermissionStateManager}
                component={GroceryListDetailsPageBody}
                canResolve={state => state.canAccess}
            />
        </JdashboardPage>
    );
}