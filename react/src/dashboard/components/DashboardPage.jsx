import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';
import ReceivingElement from '../../state/components/ReceivingElement';
import Broadcaster from '../../state/Broadcaster';
import UserDataBroadcaster from '../../accounts/state/UserDataBroadcaster';
import DashboardAlertBroadcaster from '../../dashboard/state/DashboardAlertBroadcaster';
import DashboardHeaderBroadcaster from '../../dashboard/state/DashboardHeaderBroadcaster';
import DashboardAlerts from './DashboardAlerts';
import DashboardHeader from './DashboardHeader';
import DashboardMenuAssistant from './DashboardMenuAssistant';
import IconButton from '../../common/components/IconButton';

import '../../common/styles/colors.css';
import '../../common/styles/common.css';
import '../styles/DashboardPage.css';

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
    children, className, title, appId, history, broadcasterSubscribers, showMenuAssistant,
}) => {
    // Store page-level broadcasters in state to persist them through re-renders
    // (although page-level re-renders should be few and far between).
    const [broadcasters] = useState(createPageBroadcasters(broadcasterSubscribers));
    useEffect(() => {
        document.title = title;

        // Clean-up all page-level broadcasters when the page unmounts.
        return () => {
            Object.values(broadcasters)
                .forEach(broadcaster => broadcaster.destroy());
        };
    }, [title]);

    const { headerBroadcaster, alertBroadcaster, userDataBroadcaster } = broadcasters;
    const pageClassName = classnames('DashboardPage', className);

    const [expanded, setExpanded] = useState(false);
    const menuAssistant = showMenuAssistant && (
        <DashboardMenuAssistant
            openFrom="auto"
            expanded={expanded}
        >
        </DashboardMenuAssistant>
    );
    return (
        <div className={pageClassName}>
            {menuAssistant}
            <ReceivingElement broadcaster={headerBroadcaster}>
                <DashboardHeader
                    title={title}
                    appId={appId}
                />
            </ReceivingElement>
            <IconButton
                onClick={() => setExpanded(!expanded)}
            >
                Expand
            </IconButton>
            <hr className="header-divider" />
            <div className="body">
                <ReceivingElement
                    broadcaster={alertBroadcaster}
                    waitForBroadcaster={true}
                >
                    <DashboardAlerts />
                </ReceivingElement>
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
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
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