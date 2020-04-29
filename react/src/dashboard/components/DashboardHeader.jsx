import React from 'react';
import PropTypes from 'prop-types';
import IconButton from '../../common/components/IconButton';
import { logger } from '../../common/js/logs';

import '../styles/DashboardHeader.css';

function onClickHeaderButton(data) {
    if (data.url) {
        window.location.replace(data.url);
    } else if (data.onClick) {
        data.onClick();
    } else {
        logger.warn(`No click handler functionality for header button ${data.id}`);
    }
}

function createHeaderButtons(props) {
    const headerButtons = [
        {
            id: 'home',
            label: 'Home',
            url: '/home',
            icon: 'fas fa-home',
            shouldShow: () => true,
        },
        {
            id: 'scrabble',
            label: 'Scrabble',
            url: '/scrabble/play',
            icon: 'fas fa-font',
            shouldShow: () => true,
        },
        {
            id: 'login',
            label: 'Log in',
            url: '/accounts/login',
            icon: 'fas fa-sign-in-alt',
            shouldShow: props => !props.isLoggedIn,
        },
        {
            id: 'logout',
            label: 'Log out',
            icon: 'fas fa-sign-out-alt',
            onClick: props.logOut,
            shouldShow: props => props.isLoggedIn,
        },
    ];

    return headerButtons
        .filter(data => data.id !== props.appId)
        .filter(data => data.shouldShow(props))
        .map(data => (
            <IconButton
                key={data.id}
                className={`header-button ${data.id}`}
                variant="outline-light"
                fontAwesomeClassName={data.icon}
                size={"sm"}
                onClick={() => onClickHeaderButton(data)}
            >
                {data.label}
            </IconButton>
    ));
}

const DashboardHeader = (props) => {
    const { title } = props;
    const headerButtons = createHeaderButtons(props);

    return (
        <div className="DashboardHeader">
            <div className="content">
                <h1 className="color-white">
                    {title}
                </h1>
                {headerButtons}
            </div>
        </div>
    );
};

DashboardHeader.propTypes = {
    appId: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    isLoggedIn: PropTypes.bool,
    username: PropTypes.string,
    logOut: PropTypes.func.isRequired,
};

DashboardHeader.defaultProps = {
    isLoggedIn: null,
    username: null,
};

export default DashboardHeader;