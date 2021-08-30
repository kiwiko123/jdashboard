import React, { useEffect } from 'react';
import { Redirect, Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import PushServiceSessionManager from './tools/pushService/private/PushServiceSessionManager';
import HomePage from './home/pages/HomePage';
import FeatureFlagPage from './admin/featureFlags/FeatureFlagPage';
import CreateAccountPage from './accounts/create/CreateAccountPage';
import LoginPage from './accounts/pages/LoginPage';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';
import PazaakPlayPage from 'pazaak/PazaakPlayPage';
import ChatroomPage from './chatroom/pages/ChatroomPage';
import NotFoundPage from './dashboard/pages/NotFoundPage';

export default function() {
    useEffect(() => {
        return () => {
            PushServiceSessionManager.purge();
        };
    }, []);

    return (
        <BrowserRouter>
            <Switch>
                <Route path="/admin/feature-flags" component={FeatureFlagPage} />
                <Route path="/accounts/create" component={CreateAccountPage} />
                <Route path="/accounts/login" component={LoginPage} />
                <Route path="/chatroom" component={ChatroomPage} />
                <Route path="/home" component={HomePage} />
                <Route path="/not-found" component={NotFoundPage} />
                <Route path="/pazaak/play" component={PazaakPlayPage} />
                <Route path="/scrabble/play" component={ScrabblePlayPage} />
                <Redirect to="/not-found" />
            </Switch>
        </BrowserRouter>
    );
}