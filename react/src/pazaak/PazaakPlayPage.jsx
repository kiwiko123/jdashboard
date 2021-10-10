import React from 'react';
import DashboardPage from 'dashboard/components/DashboardPage';
import { useStateManager } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import PazaakGameStateTransmitter from './state/PazaakGameStateTransmitter';
import PazaakGame from './components/PazaakGame';

const PazaakPage = () => {
    const gameStateTransmitter = useStateManager(() => new PazaakGameStateTransmitter());

    return (
        <DashboardPage
            appId="pazaak"
            title="Pazaak"
            className="PazaakPlayPage"
        >
            <ComponentStateManager
                component={PazaakGame}
                broadcaster={gameStateTransmitter}
                canResolve={({ player, opponent }) => player && opponent}
            />
        </DashboardPage>
    );
};

export default PazaakPage;