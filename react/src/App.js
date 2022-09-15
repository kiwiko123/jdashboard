import React, { useEffect } from 'react';
import { Redirect, Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import { get } from 'lodash';
import LifecycleManager from 'tools/lifecycle/LifecycleManager';
import routes from './routes';

const PAGE_ROUTES = Object.values(routes)
    .map(route => (
        <Route
            key={route.id}
            path={route.path}
            exact={get(route, 'exact', true)}
            component={route.component}
        />
    ));

export default function() {
    useEffect(() => {
        LifecycleManager.setUp();
        return () => {
            LifecycleManager.tearDown();
        };
    }, []);

    return (
        <BrowserRouter>
            <Switch>
                {PAGE_ROUTES}
                <Redirect to="/not-found" />
            </Switch>
        </BrowserRouter>
    );
}