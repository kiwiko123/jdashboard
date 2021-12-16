import React, { useEffect } from 'react';
import { Redirect, Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import { get } from 'lodash';
import PushServiceSessionManager from './tools/pushService/private/PushServiceSessionManager';
import routes from './routes';

export default function() {
    useEffect(() => {
        return () => {
            PushServiceSessionManager.purge();
        };
    }, []);

    const pageRoutes = routes.map(route => (
        <Route
            exact={get(route, 'exact', true)}
            key={route.id}
            path={route.path}
            component={route.component}
        />
    ));

    return (
        <BrowserRouter>
            <Switch>
                {pageRoutes}
                <Redirect to="/not-found" />
            </Switch>
        </BrowserRouter>
    );
}