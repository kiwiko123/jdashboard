import React from 'react';
import JdashboardPage from 'dashboard/components/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';

export default function() {

    return (
        <JdashboardPage
            appId="groceryList"
            title="Grocery List"
        >
            Hello!
        </JdashboardPage>
    );
}