import React from 'react';
import { authenticatedUser } from 'tools/dashboard/conditions';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import MainViewWithSideNavBar from 'ui/views/MainViewWithSideNavBar';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import FeatureFlagFormStateTransmitter from './state/FeatureFlagFormStateTransmitter';
import FeatureFlagListStateTransmitter from './state/FeatureFlagListStateTransmitter';
import FeatureFlagModalStateTransmitter from './state/FeatureFlagModalStateTransmitter';
import FeatureFlagToolbarStateTransmitter from './state/FeatureFlagToolbarStateTransmitter';
import FeatureFlagForm from './components/FeatureFlagForm';
import FeatureFlagList from './components/FeatureFlagList';
import FeatureFlagModal from './components/FeatureFlagModal';
import FeatureFlagPageContent from './components/FeatureFlagPageContent';
import FeatureFlagPageToolbar from './components/FeatureFlagPageToolbar';

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
    const toolbarStateManager = useStateManager(() => new FeatureFlagToolbarStateTransmitter());
    const featureFlagModalStateManager = useStateManager(() => new FeatureFlagModalStateTransmitter());
    const featureFlagListStateManager = useStateManager(() => new FeatureFlagListStateTransmitter());
    const featureFlagFormStateManager = useStateManager(() => new FeatureFlagFormStateTransmitter());

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
                return (
                    <ComponentStateManager
                        component={FeatureFlagForm}
                        stateManager={featureFlagFormStateManager}
                    />
                );
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
                component={FeatureFlagModal}
                stateManager={featureFlagModalStateManager}
            />
            <MainViewWithSideNavBar
                navigationItems={NAVIGATION_ITEMS}
                renderMainView={renderMainView}
            />
        </JdashboardPage>
    );
}