import HomePage from './home/pages/HomePage';
import FeatureFlagPage from './admin/featureFlags/FeatureFlagPage';
import CreateAccountPage from './accounts/create/CreateAccountPage';
import LoginPage from './accounts/pages/LoginPage';
import LogInRedirectPage from './accounts/pages/LogInRedirectPage';
import LogOutRedirectPage from './accounts/pages/LogOutRedirectPage';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';
import PazaakPlayPage from 'pazaak/PazaakPlayPage';
import ChatroomPage from 'chatroom/ChatroomPage';
import ChatroomRoomPage from 'chatroom/rooms/ChatroomRoomPage';
import GroceryListPage from 'grocerylist/GroceryListPage';
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
        id: 'logInRedirect',
        path: '/auth/log-in',
        component: LogInRedirectPage,
    },
    {
        id: 'logOutRedirect',
        path: '/auth/log-out',
        component: LogOutRedirectPage,
    },
    {
        id: 'home',
        path: '/home',
        component: HomePage,
    },
    {
        id: 'chatroom',
        path: '/chatroom',
        component: ChatroomPage,
        exact: true,
    },
    {
        id: 'chatroomRoom',
        path: '/chatroom/room',
        component: ChatroomRoomPage,
        exact: true,
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
    {
        id: 'groceryList',
        path: '/grocerylist',
        component: GroceryListPage,
    },
];

function processRoutes() {
    return ROUTES;
}

export default processRoutes();