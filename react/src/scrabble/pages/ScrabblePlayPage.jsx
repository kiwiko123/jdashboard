import React from 'react';
import { isEmpty } from 'lodash';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ScrabbleGame from '../components/ScrabbleGame';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import ScrabbleGameBroadcaster from '../state/ScrabbleGameBroadcaster';
import ScrabbleErrorBroadcaster from '../state/ScrabbleErrorBroadcaster';
import { useBroadcaster } from '../../state/hooks';

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
        <ComponentStateManager
            broadcaster={scrabbleGameBroadcaster}
            component={ScrabbleGame}
            canResolve={state => !isEmpty(state)}
        />
      </DashboardPage>
  );
}