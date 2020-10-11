import React from 'react';
import DashboardPage from '../components/DashboardPage';

export default function() {
    return (
        <DashboardPage
            className="NotFoundPage"
            title="Jdashboard"
            appId="notFoundPage"
            showMenuAssistant={false}
        >
            <p>Not found</p>
        </DashboardPage>
    );
};