import React, { useEffect } from 'react';
import { Redirect, Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import PushServiceSessionManager from './tools/pushService/private/PushServiceSessionManager';
import HomePage from './home/pages/HomePage';
import CreateAccountPage from './accounts/create/CreateAccountPage';
import LoginPage from './accounts/pages/LoginPage';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';
import ChatroomPage from './chatroom/pages/ChatroomPage';
import NotFoundPage from './dashboard/pages/NotFoundPage';

export default function() {
    useEffect(() => () => {
        PushServiceSessionManager.purge();
    }, []);

    return (
        <BrowserRouter>
            <Switch>
                <Route path="/accounts/create" component={CreateAccountPage} />
                <Route path="/accounts/login" component={LoginPage} />
                <Route path="/home" component={HomePage} />
                <Route path="/not-found" component={NotFoundPage} />
                <Route path="/chatroom" component={ChatroomPage} />
                <Route path="/scrabble/play" component={ScrabblePlayPage} />
                <Redirect to="/not-found" />
            </Switch>
        </BrowserRouter>
    );
}