import HomePage from './home/pages/HomePage';
import FeatureFlagPage from './admin/featureFlags/FeatureFlagPage';
import EditFeatureFlagPage from './admin/featureFlags/edit/EditFeatureFlagPage';
import CreateAccountPage from './accounts/create/CreateAccountPage';
import LoginPage from './accounts/pages/LoginPage';
import LogInRedirectPage from './accounts/pages/LogInRedirectPage';
import LogOutRedirectPage from './accounts/pages/LogOutRedirectPage';
import ScrabblePlayPage from './scrabble/pages/ScrabblePlayPage';
import PazaakPlayPage from 'pazaak/PazaakPlayPage';
import ChatroomPage from 'chatroom/ChatroomPage';
import ChatroomRoomPage from 'chatroom/rooms/ChatroomRoomPage';
import GroceryListPage from 'grocerylist/GroceryListPage';
import GroceryListDetailsPage from 'grocerylist/GroceryListDetailsPage';
import ServiceRequestKeyManagerPage from 'developers/serviceRequestKeyManager/ServiceRequestKeyManagerPage';
import CreateServiceRequestKeyPage from 'developers/serviceRequestKeyManager/create/CreateServiceRequestKeyPage';
import NotFoundPage from './dashboard/pages/NotFoundPage';

const ROUTES = {
    NotFound: {
        id: 'notFound',
        path: '/not-found',
        component: NotFoundPage,
    },
    CreateAccount: {
        id: 'createAccount',
        path: '/accounts/create',
        component: CreateAccountPage,
    },
    LogIn: {
        id: 'login',
        path: '/accounts/login',
        component: LoginPage,
    },
    LogInRedirect: {
        id: 'logInRedirect',
        path: '/auth/log-in',
        component: LogInRedirectPage,
    },
    LogOutRedirect: {
        id: 'logOutRedirect',
        path: '/auth/log-out',
        component: LogOutRedirectPage,
    },
    Home: {
        id: 'home',
        path: '/home',
        component: HomePage,
    },
    Chatroom: {
        id: 'chatroom',
        path: '/chatroom',
        component: ChatroomPage,
        exact: true,
    },
    ChatroomRoom: {
        id: 'chatroomRoom',
        path: '/chatroom/room',
        component: ChatroomRoomPage,
        exact: true,
    },
    FeatureFlagAdmin: {
        id: 'featureFlags',
        path: '/admin/feature-flags',
        component: FeatureFlagPage,
    },
    EditFeatureFlag: {
        id: 'editFeatureFlag',
        path: '/admin/feature-flags/:featureFlagId/edit',
        component: EditFeatureFlagPage,
    },
    Pazaak: {
        id: 'pazaakPlay',
        path: '/pazaak/play',
        component: PazaakPlayPage,
    },
    Scrabble: {
        id: 'scrabblePlay',
        path: '/scrabble/play',
        component: ScrabblePlayPage,
    },
    GroceryList: {
        id: 'groceryList',
        path: '/grocerylist',
        component: GroceryListPage,
    },
    GroceryListDetails: {
        id: 'groceryListDetails',
        path: '/grocerylist/:listId',
        component: GroceryListDetailsPage,
    },
    ServiceRequestKeyManager: {
        id: 'serviceRequestKeyManager',
        path: '/developers/service-request-key-manager',
        component: ServiceRequestKeyManagerPage,
    },
    CreateServiceRequestKeyPage: {
        id: 'createServiceRequestKey',
        path: '/developers/service-request-key-manager/create',
        component: CreateServiceRequestKeyPage,
    },
};

export default ROUTES;