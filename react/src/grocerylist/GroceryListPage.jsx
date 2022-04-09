import React from 'react';
import JdashboardPage from 'dashboard/components/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import { authenticatedUser } from 'tools/dashboard/conditions';
import GroceryListModalStateManager from './state/GroceryListModalStateManager';
import GroceryListToolbarStateManager from './state/GroceryListToolbarStateManager';
import GroceryListFeedStateManager from './state/GroceryListFeedStateManager';
import GroceryListModalDispatcher from './components/GroceryListModalDispatcher';
import GroceryListPageToolbar from './components/GroceryListPageToolbar';
import GroceryListFeed from './components/GroceryListFeed';

export default function() {
    const modalStateManager = useStateManager(() => new GroceryListModalStateManager());
    const toolbarStateManager = useStateManager(() => new GroceryListToolbarStateManager());
    const feedStateManager = useStateManager(() => new GroceryListFeedStateManager());

    return (
        <JdashboardPage
            appId="groceryList"
            title="Grocery List"
            requiredConditions={[authenticatedUser]}
        >
            <ComponentStateManager
                stateManager={modalStateManager}
                component={GroceryListModalDispatcher}
            />
            <ComponentStateManager
                stateManager={toolbarStateManager}
                component={GroceryListPageToolbar}
            />
            <ComponentStateManager
                stateManager={feedStateManager}
                component={GroceryListFeed}
            />
        </JdashboardPage>
    );
}