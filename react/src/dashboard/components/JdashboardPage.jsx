import React, { useCallback, useEffect } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import ClientSessionManager from 'tools/clientSessions/ClientSessionManager';
import DashboardHeaderStateManager from '../../dashboard/state/DashboardHeaderStateManager';
import DashboardHeader from './DashboardHeader';
import DashboardMenuAssistantPane from './DashboardMenuAssistantPane';

import '../../common/styles/colors.css';
import '../../common/styles/common.css';
import './styles/DashboardPage.css';

const JdashboardPage = ({
    children, className, title, appId, showMenuAssistant,
}) => {
    useEffect(() => {
        document.title = title;
    }, [title]);
    useEffect(() => {
        const clientSessionManager = new ClientSessionManager();
        clientSessionManager.createNewSession();
        return () => {
            clientSessionManager.endSession();
        };
    }, []);
    const headerStateManager = useStateManager(() => new DashboardHeaderStateManager());

    const pageClassName = classnames('DashboardPage', className);
    const menuAssistant = showMenuAssistant && (
        <DashboardMenuAssistantPane
            openFrom="auto"
            expanded={false}
        >
        </DashboardMenuAssistantPane>
    );
    const DashboardHeaderComponent = useCallback((componentProps) => (
        <DashboardHeader
            {...componentProps}
            title={title}
            appId={appId}
        />
    ), [title, appId]);

    return (
        <div className={pageClassName}>
            {menuAssistant}
            <ComponentStateManager
                broadcaster={headerStateManager}
                component={DashboardHeaderComponent}
            />
            <hr className="header-divider" />
            <div className="body">
                {children}
            </div>
        </div>
    );
}

JdashboardPage.propTypes = {
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
    title: PropTypes.string,
    appId: PropTypes.string.isRequired,
    showMenuAssistant: PropTypes.bool,
};

JdashboardPage.defaultProps = {
    className: null,
    title: 'Jdashboard',
    showMenuAssistant: true,
};

export default JdashboardPage;