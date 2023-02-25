import React, { useState } from 'react';
import { authenticatedUser } from 'tools/dashboard/conditions';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import MainViewWithSideNavBar from 'ui/views/MainViewWithSideNavBar';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import FeatureFlagModalDelegateStateManager from './state/FeatureFlagModalDelegateStateManager';
import FeatureFlagListStateManager from './state/FeatureFlagListStateManager';
import FeatureFlagPageToolbarStateManager from './state/FeatureFlagPageToolbarStateManager';
import FeatureFlagModalDelegate from './components/FeatureFlagModalDelegate';
import FeatureFlagList from './components/FeatureFlagList';
import FeatureFlagPageNavigationItem from './components/FeatureFlagPageNavigationItem';
import FeatureFlagPageToolbar from './components/FeatureFlagPageToolbar';

import './FeatureFlagPage.css';

export default function() {
    const featureFlagPageToolbarStateManager = useStateManager(() => new FeatureFlagPageToolbarStateManager());
    const featureFlagListStateManager = useStateManager(() => new FeatureFlagListStateManager());

    const [isModalOpen, setIsModalOpen] = useState(false);
    const featureFlagModalDelegateStateManager = useStateManager(
        () => new FeatureFlagModalDelegateStateManager({
            openModal: () => setIsModalOpen(true),
            closeModal: () => setIsModalOpen(false),
        }),
    );

    const navigationItems = [
        {
            id: 'featureFlags',
            content: (
                <FeatureFlagPageNavigationItem
                    icon={<i className="fas fa-toggle-on" />}
                    label="Feature flags"
                />
            ),
        },
    ];

    const renderMainView = ({ id }) => {
        switch (id) {
            case 'featureFlags':
                return (
                    <div>
                        <ComponentStateManager
                            component={FeatureFlagPageToolbar}
                            stateManager={featureFlagPageToolbarStateManager}
                        />
                        <ComponentStateManager
                            component={FeatureFlagList}
                            stateManager={featureFlagListStateManager}
                        />
                    </div>
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
                component={FeatureFlagModalDelegate}
                stateManager={featureFlagModalDelegateStateManager}
                staticProps={{ isOpen: isModalOpen }}
            />
            <MainViewWithSideNavBar
                navigationItems={navigationItems}
                renderMainView={renderMainView}
            />
        </JdashboardPage>
    );
}