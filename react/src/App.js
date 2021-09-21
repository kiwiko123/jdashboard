import React, { useEffect } from 'react';
import { Redirect, Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import PushServiceSessionManager from './tools/pushService/private/PushServiceSessionManager';
import routes from './routes';

export default function() {
    useEffect(() => {
        return () => {
            PushServiceSessionManager.purge();
        };
    }, []);

    const pageRoutes = routes.map(route => <Route key={route.id} path={route.path} component={route.component} />);

    return (
        <BrowserRouter>
            <Switch>
                {pageRoutes}
                <Redirect to="/not-found" />
            </Switch>
        </BrowserRouter>
    );
}