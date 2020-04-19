import React from 'react';
import PropTypes from 'prop-types';
import IconButton from '../../common/components/IconButton';

import '../styles/DashboardHeader.css';

const headerButtons = [
    {
        id: 'home',
        label: 'Home',
        url: '/home',
        icon: 'fas fa-home',
        shouldShow: true,
    },
    {
        id: 'scrabble',
        label: 'Scrabble',
        url: '/scrabble/play',
        icon: 'fas fa-font',
        shouldShow: true,
    },
    {
            id: 'login',
            label: 'Login',
            url: '/accounts/login',
            icon: 'fas fa-sign-in-alt',
            shouldShow: true,
        },
];

function createHeaderButtons({ appId }) {
    return headerButtons
        .filter(data => data.id !== appId)
        .filter(data => data.shouldShow)
        .map(data => (
            <IconButton
                key={data.id}
                className={`header-button ${data.id}`}
                variant="outline-light"
                fontAwesomeClassName={data.icon}
                size={"sm"}
            >
                <a
                    className={`header-button-link ${data.id}`}
                    href={data.url}
                >
                    {data.label}
                </a>
            </IconButton>
    ));
}

const DashboardHeader = ({
    appId, title, isLoggedIn, username,
}) => {
    const headerButtons = createHeaderButtons({ appId });
    const greeting = username && (
        <div className="greeting">
            {`Hello, ${username}`}
        </div>
    );

    return (
        <div className="DashboardHeader">
            <div className="content">
                <h1 className="color-white">
                    {title}
                </h1>
                {headerButtons}
            </div>
            {greeting}
        </div>
    );
};

DashboardHeader.propTypes = {
    appId: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    isLoggedIn: PropTypes.bool,
    username: PropTypes.string,
};

DashboardHeader.defaultProps = {
    isLoggedIn: false,
    username: null,
};

export default DashboardHeader;