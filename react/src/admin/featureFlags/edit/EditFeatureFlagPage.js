import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { authenticatedUser } from 'tools/dashboard/conditions';
import JdashboardPage from 'tools/dashboard/JdashboardPage';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import EditFeatureFlagPageStateManager from './EditFeatureFlagPageStateManager';

const EditFeatureFlagPage = () => {
    const { featureFlagId } = useParams();
    const editFeatureFlagPageStateManager = useStateManager(() => new EditFeatureFlagPageStateManager({ featureFlagId }));

    return (
        <JdashboardPage
            appId="editFeatureFlagPage"
            title="Edit Feature Flag"
            requiredConditions={[authenticatedUser]}
        >
            Hello!
        </JdashboardPage>
    );
};

export default EditFeatureFlagPage;