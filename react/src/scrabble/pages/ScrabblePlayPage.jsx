import React from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ScrabbleGame from '../components/ScrabbleGame';
import BroadcasterComponentStateManager from '../../state/components/BroadcasterComponentStateManager';
import ScrabbleGameBroadcaster from '../state/ScrabbleGameBroadcaster';
import ScrabbleErrorBroadcaster from '../state/ScrabbleErrorBroadcaster';
import { useBroadcaster } from '../../state/hooks/broadcasterHooks';

import '../styles/ScrabblePlayPage.css';

export default function ScrabblePlayPage({ history }) {
  const scrabbleGameBroadcaster = useBroadcaster(ScrabbleGameBroadcaster);
  const scrabbleErrorBroadcaster = useBroadcaster(ScrabbleErrorBroadcaster);

  scrabbleGameBroadcaster.register(scrabbleErrorBroadcaster);

  const pageBroadcasterSubscribers = {
    userDataBroadcaster: [scrabbleGameBroadcaster],
  };

  return (
      <DashboardPage
        className="ScrabblePlayPage"
        title="Scrabble"
        appId="scrabble"
        history={history}
        broadcasterSubscribers={pageBroadcasterSubscribers}
      >
        <BroadcasterComponentStateManager
            broadcaster={scrabbleGameBroadcaster}
            component={ScrabbleGame}
            canResolve={({ isLoaded }) => isLoaded}
        />
      </DashboardPage>
  );
}