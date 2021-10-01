import HomePage from './home/pages/HomePage';
import FeatureFlagPage from './admin/featureFlags/FeatureFlagPage';
import CreateAccountPage from './accounts/create/CreateAccountPage';
import LoginPage from './accounts/pages/LoginPage';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';
import PazaakPlayPage from 'pazaak/PazaakPlayPage';
import NotFoundPage from './dashboard/pages/NotFoundPage';

const ROUTES = [
    {
        id: 'notFound',
        path: '/not-found',
        component: NotFoundPage,
    },
    {
        id: 'createAccount',
        path: '/accounts/create',
        component: CreateAccountPage,
    },
    {
        id: 'login',
        path: '/accounts/login',
        component: LoginPage,
    },
    {
        id: 'home',
        path: '/home',
        component: HomePage,
    },
    {
        id: 'featureFlags',
        path: '/admin/feature-flags',
        component: FeatureFlagPage,
    },
    {
        id: 'pazaakPlay',
        path: '/pazaak/play',
        component: PazaakPlayPage,
    },
    {
        id: 'scrabblePlay',
        path: '/scrabble/play',
        component: ScrabblePlayPage,
    },
];

function processRoutes() {
    return ROUTES;
}

export default processRoutes();