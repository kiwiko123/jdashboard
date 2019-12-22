import React from 'react';
import { Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';


export default function() {
    return (
        <BrowserRouter>
            <Route path="/scrabble/play" component={ScrabblePlayPage} />
        </BrowserRouter>
    );
}