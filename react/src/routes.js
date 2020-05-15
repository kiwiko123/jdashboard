import React from 'react';
import { Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import HomePage from './home/pages/HomePage';
import LoginPage from './accounts/pages/LoginPage';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';

export default function() {
    return (
        <BrowserRouter>
            <Route path="/scrabble/play" component={ScrabblePlayPage} />
            <Route path="/accounts/login" component={LoginPage} />
            <Route path="/home" component={HomePage} />
        </BrowserRouter>
    );
}