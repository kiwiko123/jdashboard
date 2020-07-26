import React from 'react';
import PropTypes from 'prop-types';
import IconButton from '../../common/components/IconButton';
import DashboardMenuSlideOverPane from './DashboardMenuSlideOverPane';
import logger from '../../common/js/logging';

import './styles/DashboardHeader.css';

function onClickHeaderButton(data) {
    if (data.url) {
        window.location.href = data.url;
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
            shouldShow: props => !props.userData.isLoggedIn,
        },
        {
            id: 'logout',
            label: 'Log out',
            icon: 'fas fa-sign-out-alt',
            onClick: props.logOut,
            shouldShow: props => props.userData.isLoggedIn,
        },
    ];

    return headerButtons
        .filter(data => data.id !== props.appId)
        .filter(data => data.shouldShow(props))
        .map(data => (
            <IconButton
                key={data.id}
                className={`header-button ${data.id}`}
                variant="outline-dark"
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
    const menuPaneButton = !props.isMenuSlideOverExpanded && (
        <IconButton
            fontAwesomeClassName="fas fa-bars"
            variant="outline-light"
            onClick={props.toggleMenuSlideOver}
        />
    );

    return (
        <div className="DashboardHeader">
            <div className="content">
                <DashboardMenuSlideOverPane
                    appId={props.appId}
                    expanded={props.isMenuSlideOverExpanded}
                    toggleExpand={props.toggleMenuSlideOver}
                    accountProps={props.userData}
                />
                {menuPaneButton}
                <h1 className="color-white">
                    {title}
                </h1>
            </div>
        </div>
    );
};

DashboardHeader.propTypes = {
    appId: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    userData: PropTypes.shape({
        username: PropTypes.string,
        isLoggedIn: PropTypes.bool,
        logOut: PropTypes.func,
    }),
    toggleMenuSlideOver: PropTypes.func.isRequired,
    isMenuSlideOverExpanded: PropTypes.bool,
};

DashboardHeader.defaultProps = {
    isLoggedIn: null,
    username: null,
    isMenuSlideOverExpanded: false,
    userData: {},
};

export default DashboardHeader;