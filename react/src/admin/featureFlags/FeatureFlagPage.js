import React from 'react';
import { authenticatedUser } from 'tools/dashboard/conditions';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import FeatureFlagToolbarStateTransmitter from './state/FeatureFlagToolbarStateTransmitter';
import FeatureFlagModalStateTransmitter from './state/FeatureFlagModalStateTransmitter';
import FeatureFlagPageToolbar from './components/FeatureFlagPageToolbar';
import FeatureFlagModal from './components/FeatureFlagModal';
import FeatureFlagPageContent from './components/FeatureFlagPageContent';

export default function() {
    const toolbarStateManager = useStateManager(() => new FeatureFlagToolbarStateTransmitter());
    const featureFlagModalStateManager = useStateManager(() => new FeatureFlagModalStateTransmitter());

    return (
        <JdashboardPage
            appId="featureFlagAdminPage"
            title="Feature Flags"
            requiredConditions={[authenticatedUser]}
        >
            <ComponentStateManager
                component={FeatureFlagPageToolbar}
                stateManager={toolbarStateManager}
            />
            <div className="interactive-elements-wrapper">
                <ComponentStateManager
                    component={FeatureFlagModal}
                    stateManager={featureFlagModalStateManager}
                />
            </div>
            <FeatureFlagPageContent />
        </JdashboardPage>
    );
}