// eslint-disable-next-line no-unused-vars
import React from 'react';
import DashboardPage from '../../common/components/DashboardPage';
import ScrabbleGame from '../components/ScrabbleGame';
import ReceivingElement from '../../state/components/ReceivingElement';
import ScrabbleAlerts from '../components/ScrabbleAlerts';
import ScrabbleGameBroadcaster from '../state/ScrabbleGameBroadcaster';
import ScrabbleGameReceiver from '../state/ScrabbleGameReceiver';
import ScrabbleErrorReceiver from '../state/ScrabbleErrorReceiver';

import '../styles/ScrabblePlayPage.css';


export default function ScrabblePlayPage({ history }) {
  const scrabbleGameBroadcaster = new ScrabbleGameBroadcaster();
  const scrabbleGameReceiver = new ScrabbleGameReceiver([scrabbleGameBroadcaster]);
  const scrabbleErrorReceiver = new ScrabbleErrorReceiver([scrabbleGameBroadcaster]);

  return (
      <DashboardPage
        className="ScrabblePlayPage"
        title="Scrabble"
        appId="scrabble"
        history={history}
      >
        <ReceivingElement receiver={scrabbleErrorReceiver}>
            <ScrabbleAlerts />
        </ReceivingElement>
        <ReceivingElement receiver={scrabbleGameReceiver} delayRenderForReceiver={true}>
            <ScrabbleGame />
        </ReceivingElement>
      </DashboardPage>
  );
}