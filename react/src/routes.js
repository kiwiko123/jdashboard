import React from 'react';
import { Redirect, Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import HomePage from './home/pages/HomePage';
import LoginPage from './accounts/pages/LoginPage';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';
import NotFoundPage from './dashboard/pages/NotFoundPage';

export default function() {
    return (
        <BrowserRouter>
            <Switch>
                <Route path="/scrabble/play" component={ScrabblePlayPage} />
                <Route path="/accounts/login" component={LoginPage} />
                <Route path="/home" component={HomePage} />
                <Route path="/not-found" component={NotFoundPage} />
                <Redirect to="/not-found" />
            </Switch>
        </BrowserRouter>
    );
}