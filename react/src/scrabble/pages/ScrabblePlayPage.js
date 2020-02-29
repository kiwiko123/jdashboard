// eslint-disable-next-line no-unused-vars
import React from 'react';
import DashboardPage from '../../common/components/DashboardPage';
import ScrabbleGame from '../components/ScrabbleGame';


export default function ScrabblePlayPage({ history }) {
  return (
      <DashboardPage
        className="ScrabblePlayPage"
        title="Scrabble"
        appId="scrabble"
        history={history}
      >
        <ScrabbleGame />
      </DashboardPage>
  );
}