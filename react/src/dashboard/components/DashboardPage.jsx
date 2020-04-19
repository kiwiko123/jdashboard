import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ReceivingElement from '../../state/components/ReceivingElement';
import UserDataBroadcaster from '../../accounts/state/UserDataBroadcaster';
import DashboardAlertBroadcaster from '../../dashboard/state/DashboardAlertBroadcaster';
import DashboardHeaderBroadcaster from '../../dashboard/state/DashboardHeaderBroadcaster';
import DashboardAlerts from './DashboardAlerts';
import DashboardHeader from './DashboardHeader';

import '../../common/styles/colors.css';
import '../../common/styles/common.css';
import '../styles/DashboardPage.css';

const DashboardPage = ({
    children, className, title, appId, history,
}) => {
    // Create page-level broadcasters.
    // Clean these up in useEffect.
    const [headerBroadcaster] = useState(new DashboardHeaderBroadcaster());
    const [alertBroadcaster] = useState(new DashboardAlertBroadcaster());
    const [userDataBroadcaster] = useState(new UserDataBroadcaster());

    useEffect(() => {
        document.title = title;

        // Clean-up all page-level broadcasters when the page unmounts.
        return () => {
            const broadcasters = [headerBroadcaster, alertBroadcaster, userDataBroadcaster];
            broadcasters.forEach(broadcaster => broadcaster.destroy());
        };
    });

    // Register any page-level broadcasters.
    userDataBroadcaster.register(headerBroadcaster);

    const pageClassName = classnames('DashboardPage', className);
    return (
        <div className={pageClassName}>
            <ReceivingElement broadcaster={headerBroadcaster}>
                <DashboardHeader
                    title={title}
                    appId={appId}
                />
            </ReceivingElement>
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
};

DashboardPage.defaultProps = {
    className: null,
    title: 'Dashboard',
};

export default DashboardPage;