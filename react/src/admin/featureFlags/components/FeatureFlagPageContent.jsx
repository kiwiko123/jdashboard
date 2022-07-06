import React from 'react';
import { useStateManager } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import FeatureFlagList from './FeatureFlagList';
import FeatureFlagListStateTransmitter from '../state/FeatureFlagListStateTransmitter';

import './FeatureFlagPageContent.css';

const FeatureFlagPageContent = () => {
    const featureFlagListManager = useStateManager(() => new FeatureFlagListStateTransmitter());

    return (
        <div className="FeatureFlagPageContent">
            <ComponentStateManager
                component={FeatureFlagList}
                stateManager={featureFlagListManager}
            />
        </div>
    );
};

export default FeatureFlagPageContent;