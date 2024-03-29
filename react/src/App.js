import React, { useEffect } from 'react';
import { Redirect, Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import { get } from 'lodash';
import LifecycleManager from 'tools/lifecycle/LifecycleManager';
import routes from './routes';

export default function() {
    useEffect(() => {
        LifecycleManager.setUp();
        return () => {
            LifecycleManager.tearDown();
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