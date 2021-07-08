import React, { useState } from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import { useStateTransmitter } from '../../state/hooks';
import FeatureFlagToolbarStateTransmitter from './state/FeatureFlagToolbarStateTransmitter';
import CreateFeatureFlagModalStateTransmitter from './state/CreateFeatureFlagModalStateTransmitter';
import FeatureFlagPageToolbar from './components/FeatureFlagPageToolbar';
import CreateFeatureFlagModal from './components/CreateFeatureFlagModal';
import FeatureFlagPageContent from './components/FeatureFlagPageContent';

export default function() {
    const toolbarStateTransmitter = useStateTransmitter(FeatureFlagToolbarStateTransmitter);
    const createFeatureFlagModalStateTransmitter = useStateTransmitter(CreateFeatureFlagModalStateTransmitter);

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
                    component={CreateFeatureFlagModal}
                    broadcaster={createFeatureFlagModalStateTransmitter}
                />
            </div>
            <FeatureFlagPageContent />
        </DashboardPage>
    );
}