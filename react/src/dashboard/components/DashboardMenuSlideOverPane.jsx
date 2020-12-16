import React from 'react';
import PropTypes from 'prop-types';
import SlideOverPane from '../../common/components/SlideOverPane';
import IconButton from '../../common/components/IconButton';
import {
    onClickNavigationButton,
    makeAccountButtonSettings,
    makeQuickLinkButtonSettings,
} from './util/buttons';

import './styles/DashboardMenuSlideOverPane.css';

function makeButtons(settings, props) {
    return settings.filter(data => data.shouldShow())
        .filter(data => data.id !== props.appId)
        .map(data => (
            <IconButton
                key={data.id}
                className={`account-button ${data.id}`}
                variant="outline-dark"
                fontAwesomeClassName={data.icon}
                size="sm"
                onClick={() => onClickNavigationButton(data)}
            >
                {data.label}
            </IconButton>
        ));
}

const DashboardMenuSlideOverPane = ({
    expanded, toggleExpand, children, accountProps, appId,
}) => {
    const accountButtons = makeButtons(makeAccountButtonSettings(accountProps), { appId });
    const quickLinkButtons = makeButtons(makeQuickLinkButtonSettings(), { appId });
    const greeting = accountProps.isLoggedIn && accountProps.username && (
        <div className="greeting">
            <i className="far fa-smile" />
            <span className="username">
                {accountProps.username}
            </span>
        </div>
    );

    return (
        <SlideOverPane
            className="DashboardMenuSlideOverPane"
            expanded={expanded}
        >
            <div className="header">
                <h1>Dashboard</h1>
                <IconButton
                    className="button-close"
                    fontAwesomeClassName="fas fa-times"
                    variant="light"
                    onClick={toggleExpand}
                />
            </div>
            <div className="account-information">
                <div className="account-header">
                    <h2>Account</h2>
                    {greeting}
                </div>
                <div className="section">
                    {accountButtons}
                </div>
            </div>
            <div className="quick-links">
                <h2>Quick Links</h2>
                <div className="section">
                    {quickLinkButtons}
                </div>
            </div>
        </SlideOverPane>
    );
};

DashboardMenuSlideOverPane.propTypes = {
    appId: PropTypes.string.isRequired,
    expanded: PropTypes.bool,
    toggleExpand: PropTypes.func.isRequired,
    accountProps: PropTypes.shape({
        isLoggedIn: PropTypes.bool.isRequired,
        logOut: PropTypes.func,
        username: PropTypes.string,
    }).isRequired,
};

DashboardMenuSlideOverPane.defaultProps = {
    expanded: false,
    isLoggedIn: false,
};

export default DashboardMenuSlideOverPane;