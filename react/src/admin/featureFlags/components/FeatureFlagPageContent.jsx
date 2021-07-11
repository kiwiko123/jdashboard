import React from 'react';
import { useStateTransmitter } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import FeatureFlagList from './FeatureFlagList';
import FeatureFlagListStateTransmitter from '../state/FeatureFlagListStateTransmitter';

import './FeatureFlagPageContent.css';

const FeatureFlagPageContent = () => {
    const featureFlagListManager = useStateTransmitter(FeatureFlagListStateTransmitter);

    return (
        <div className="FeatureFlagPageContent">
            <ComponentStateManager
                component={FeatureFlagList}
                broadcaster={featureFlagListManager}
            />
        </div>
    );
};

export default FeatureFlagPageContent;