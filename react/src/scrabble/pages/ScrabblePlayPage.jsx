// eslint-disable-next-line no-unused-vars
import React from 'react';
import DashboardPage from '../../common/components/DashboardPage';
import ScrabbleGame from '../components/ScrabbleGame';
import ReceivingElement from '../../state/components/ReceivingElement';
import ScrabbleAlerts from '../components/ScrabbleAlerts';
import ScrabbleGameBroadcaster from '../state/ScrabbleGameBroadcaster';
import ScrabbleErrorBroadcaster from '../state/ScrabbleErrorBroadcaster';

import '../styles/ScrabblePlayPage.css';


export default function ScrabblePlayPage({ history }) {
  const scrabbleGameBroadcaster = new ScrabbleGameBroadcaster();
  const scrabbleErrorBroadcaster = new ScrabbleErrorBroadcaster([scrabbleGameBroadcaster]);

  return (
      <DashboardPage
        className="ScrabblePlayPage"
        title="Scrabble"
        appId="scrabble"
        history={history}
      >
        <ReceivingElement broadcaster={scrabbleErrorBroadcaster}>
            <ScrabbleAlerts />
        </ReceivingElement>
        <ReceivingElement broadcaster={scrabbleGameBroadcaster}>
            <ScrabbleGame />
        </ReceivingElement>
      </DashboardPage>
  );
}