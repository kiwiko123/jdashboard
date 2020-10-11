import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ComponentStateManager from '../../state/components/ComponentStateManager';
import Broadcaster from '../../state/Broadcaster';
import UserDataBroadcaster from '../../accounts/state/UserDataBroadcaster';
import DashboardAlertBroadcaster from '../../dashboard/state/DashboardAlertBroadcaster';
import DashboardHeaderBroadcaster from '../../dashboard/state/DashboardHeaderBroadcaster';
import DashboardAlerts from './DashboardAlerts';
import DashboardHeader from './DashboardHeader';
import DashboardMenuAssistantPane from './DashboardMenuAssistantPane';

import '../../common/styles/colors.css';
import '../../common/styles/common.css';
import './styles/DashboardPage.css';

function createPageBroadcasters(broadcasterSubscribers = {}) {
    const broadcasters = {
        headerBroadcaster: new DashboardHeaderBroadcaster(),
        alertBroadcaster: new DashboardAlertBroadcaster(),
        userDataBroadcaster: new UserDataBroadcaster(),
    };

    const { headerBroadcaster, userDataBroadcaster } = broadcasters;
    // Register default subscribers.
    headerBroadcaster.listenTo(userDataBroadcaster);

    // Register input subscribers.
    Object.entries(broadcasterSubscribers)
        .filter(([name]) => broadcasters[name])
        .forEach(([name, subscribers]) => {
            const broadcaster = broadcasters[name];
            subscribers.forEach(subscriber => subscriber.listenTo(broadcaster));
        });

    return broadcasters;
}

const DashboardPage = ({
    children, className, title, appId, broadcasterSubscribers, showMenuAssistant,
}) => {
    // Store page-level broadcasters in state to persist them through re-renders
    // (although page-level re-renders should be few and far between).
    const [broadcasters] = useState(createPageBroadcasters(broadcasterSubscribers));

    // Clean-up all page-level broadcasters when the page unmounts.
    useEffect(() => {
        return () => {
            Object.values(broadcasters)
                .forEach(broadcaster => broadcaster.destroy());
        };
    }, [broadcasters]);

    // Update the browser tab's title.
    useEffect(() => {
        document.title = title;
    });

    const { headerBroadcaster, alertBroadcaster } = broadcasters;
    const pageClassName = classnames('DashboardPage', className);
    const menuAssistant = showMenuAssistant && (
        <DashboardMenuAssistantPane
            openFrom="auto"
            expanded={false}
        >
        </DashboardMenuAssistantPane>
    );
    return (
        <div className={pageClassName}>
            {menuAssistant}
            <ComponentStateManager broadcaster={headerBroadcaster}>
                <DashboardHeader
                    title={title}
                    appId={appId}
                />
            </ComponentStateManager>
            <hr className="header-divider" />
            <div className="body">
                <ComponentStateManager broadcaster={alertBroadcaster}>
                    <DashboardAlerts />
                </ComponentStateManager>
                {children}
            </div>
        </div>
    );
}

DashboardPage.propTypes = {
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
    title: PropTypes.string,
    appId: PropTypes.string.isRequired,
    broadcasterSubscribers: PropTypes.shape({
        headerBroadcaster: PropTypes.arrayOf(PropTypes.instanceOf(Broadcaster)),
        alertBroadcaster: PropTypes.arrayOf(PropTypes.instanceOf(Broadcaster)),
        userDataBroadcaster: PropTypes.arrayOf(PropTypes.instanceOf(Broadcaster)),
    }),
    showMenuAssistant: PropTypes.bool,
};

DashboardPage.defaultProps = {
    className: null,
    title: 'Dashboard',
    broadcasterSubscribers: {},
    showMenuAssistant: true,
};

export default DashboardPage;