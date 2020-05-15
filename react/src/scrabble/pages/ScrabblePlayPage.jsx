// eslint-disable-next-line no-unused-vars
import React from 'react';
import DashboardPage from '../../dashboard/components/DashboardPage';
import ScrabbleGame from '../components/ScrabbleGame';
import ReceivingElement from '../../state/components/ReceivingElement';
import ScrabbleGameBroadcaster from '../state/ScrabbleGameBroadcaster';
import ScrabbleErrorBroadcaster from '../state/ScrabbleErrorBroadcaster';

import '../styles/ScrabblePlayPage.css';

export default function ScrabblePlayPage({ history }) {
  const scrabbleGameBroadcaster = new ScrabbleGameBroadcaster();
  const scrabbleErrorBroadcaster = new ScrabbleErrorBroadcaster();

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
        <ReceivingElement
            broadcaster={scrabbleGameBroadcaster}
            waitForBroadcaster={true}
        >
            <ScrabbleGame />
        </ReceivingElement>
      </DashboardPage>
  );
}