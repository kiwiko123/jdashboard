import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager, useTabTitle } from 'state/hooks';
import useManagedClientSession from './util/useManagedClientSession';
import DashboardHeaderStateManager from '../../dashboard/state/DashboardHeaderStateManager';
import DashboardHeader from './DashboardHeader';
import DashboardMenuAssistantPane from './DashboardMenuAssistantPane';

import '../../common/styles/colors.css';
import '../../common/styles/common.css';
import './styles/DashboardPage.css';

const JdashboardPage = ({
    children, className, title, appId, showMenuAssistant,
}) => {
    useTabTitle(title);
    useManagedClientSession();
    const headerStateManager = useStateManager(() => new DashboardHeaderStateManager());

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
            <ComponentStateManager
                stateManager={headerStateManager}
                component={DashboardHeader}
                staticProps={{ title, appId }}
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