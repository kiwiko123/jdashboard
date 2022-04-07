import React from 'react';
import { authenticatedUser } from 'tools/dashboard/conditions';
import JdashboardPage from 'dashboard/components/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import FeatureFlagToolbarStateTransmitter from './state/FeatureFlagToolbarStateTransmitter';
import FeatureFlagModalStateTransmitter from './state/FeatureFlagModalStateTransmitter';
import FeatureFlagPageToolbar from './components/FeatureFlagPageToolbar';
import FeatureFlagModal from './components/FeatureFlagModal';
import FeatureFlagPageContent from './components/FeatureFlagPageContent';

export default function() {
    const toolbarStateTransmitter = useStateManager(() => new FeatureFlagToolbarStateTransmitter());
    const featureFlagModalStateTransmitter = useStateManager(() => new FeatureFlagModalStateTransmitter());

    return (
        <JdashboardPage
            appId="featureFlagAdminPage"
            title="Feature Flags"
            requiredConditions={[authenticatedUser()]}
        >
            <ComponentStateManager
                component={FeatureFlagPageToolbar}
                broadcaster={toolbarStateTransmitter}
            />
            <div className="interactive-elements-wrapper">
                <ComponentStateManager
                    component={FeatureFlagModal}
                    broadcaster={featureFlagModalStateTransmitter}
                />
            </div>
            <FeatureFlagPageContent />
        </JdashboardPage>
    );
}