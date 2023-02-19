import React, { useState } from 'react';
import { authenticatedUser } from 'tools/dashboard/conditions';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import MainViewWithSideNavBar from 'ui/views/MainViewWithSideNavBar';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import FeatureFlagModalDelegateStateManager from './state/FeatureFlagModalDelegateStateManager';
import FeatureFlagListStateManager from './state/FeatureFlagListStateManager';
import FeatureFlagModalDelegate from './components/FeatureFlagModalDelegate';
import FeatureFlagList from './components/FeatureFlagList';

const NAVIGATION_ITEM_KEYS = {
    CREATE: {
        id: 'createFeatureFlag',
        label: 'Create feature flag',
    },
    VIEW: {
        id: 'viewFeatureFlags',
        label: 'View feature flags',
    },
}

const NAVIGATION_ITEMS = [
    NAVIGATION_ITEM_KEYS.VIEW,
    NAVIGATION_ITEM_KEYS.CREATE,
];

export default function() {
    const featureFlagListStateManager = useStateManager(() => new FeatureFlagListStateManager());

    const [isModalOpen, setIsModalOpen] = useState(false);
    const featureFlagModalDelegateStateManager = useStateManager(
        () => new FeatureFlagModalDelegateStateManager({
            openModal: () => setIsModalOpen(true),
            closeModal: () => setIsModalOpen(false),
        }),
    );

    const renderMainView = ({ id }) => {
        switch (id) {
            case NAVIGATION_ITEM_KEYS.VIEW.id:
                return (
                    <ComponentStateManager
                        component={FeatureFlagList}
                        stateManager={featureFlagListStateManager}
                    />
                );
            case NAVIGATION_ITEM_KEYS.CREATE.id:
                // TODO
            default:
                return null;
        }
    };

    return (
        <JdashboardPage
            appId="featureFlagAdminPage"
            title="Feature Flags"
            requiredConditions={[authenticatedUser]}
        >
            <ComponentStateManager
                component={FeatureFlagModalDelegate}
                stateManager={featureFlagModalDelegateStateManager}
                staticProps={{ isOpen: isModalOpen }}
            />
            <MainViewWithSideNavBar
                navigationItems={NAVIGATION_ITEMS}
                renderMainView={renderMainView}
            />
        </JdashboardPage>
    );
}