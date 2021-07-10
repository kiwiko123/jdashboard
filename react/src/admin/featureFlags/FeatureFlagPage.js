import React, { useState } from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import { useStateManager } from '../../state/hooks';
import FeatureFlagToolbarStateTransmitter from './state/FeatureFlagToolbarStateTransmitter';
import FeatureFlagModalStateTransmitter from './state/FeatureFlagModalStateTransmitter';
import FeatureFlagPageToolbar from './components/FeatureFlagPageToolbar';
import FeatureFlagModal from './components/FeatureFlagModal';
import FeatureFlagPageContent from './components/FeatureFlagPageContent';

export default function() {
    const toolbarStateTransmitter = useStateManager(() => new FeatureFlagToolbarStateTransmitter());
    const featureFlagModalStateTransmitter = useStateManager(() => new FeatureFlagModalStateTransmitter());

    return (
        <DashboardPage
            appId="featureFlagAdminPage"
            title="Feature Flags"
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
        </DashboardPage>
    );
}