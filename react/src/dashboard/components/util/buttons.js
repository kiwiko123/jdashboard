import logger from '../../../common/js/logging';

export function onClickNavigationButton(data) {
   if (data.url) {
       window.location.href = data.url;
   } else if (data.onClick) {
       data.onClick();
   } else {
       logger.warn(`No click handler functionality for header button ${data.id}`);
   }
}

export function makeAccountButtonSettings(props) {
    return [
       {
           id: 'login',
           label: 'Log in',
           url: '/accounts/login',
           icon: 'fas fa-sign-in-alt',
           shouldShow: () => !props.isLoggedIn,
       },
       {
           id: 'logout',
           label: 'Log out',
           icon: 'fas fa-sign-out-alt',
           onClick: props.logOut,
           shouldShow: () => props.isLoggedIn,
       },
       {
           id: 'create-account',
           label: 'Create account',
           icon: 'fas fa-user-plus',
           url: '/accounts/create',
           shouldShow: () => !props.isLoggedIn,
      },
    ];
}

export function makeQuickLinkButtonSettings(props) {
    return [
        {
            id: 'home',
            label: 'Home',
            url: '/home',
            icon: 'fas fa-home',
            shouldShow: () => true,
        },
        {
            id: 'chatroom',
            label: 'Chatroom',
            url: '/chatroom',
            icon: 'fas fa-comments',
            shouldShow: () => props.isLoggedIn,
        },
        {
            id: 'scrabble',
            label: 'Scrabble',
            url: '/scrabble/play',
            icon: 'fas fa-font',
            shouldShow: () => true,
        },
        {
            id: 'pazaak',
            label: 'Pazaak',
            url: '/pazaak/play',
            icon: 'fas fa-dice',
            shouldShow: () => props.isLoggedIn,
        },
        {
            id: 'groceryList',
            label: 'Grocery List',
            url: '/grocerylist',
            icon: 'fas fa-list-ul',
            shouldShow: () => props.isLoggedIn,
        },
    ];
}