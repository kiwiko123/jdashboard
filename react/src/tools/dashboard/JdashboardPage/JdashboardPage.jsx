import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager, useTabTitle } from 'state/hooks';
import DashboardHeaderStateManager from 'dashboard/state/DashboardHeaderStateManager';
import DashboardHeader from 'dashboard/components/DashboardHeader';
import conditionsPropType from '../conditions/conditionsPropType';
import DashboardMenuAssistantPane from 'dashboard/components/DashboardMenuAssistantPane';
import useManagedClientSession from './util/useManagedClientSession';
import useRequiredConditions from './util/useRequiredConditions';

import 'common/styles/colors.css';
import 'common/styles/common.css';
import './JdashboardPage.css';

const JdashboardResolvedPage = ({
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

JdashboardResolvedPage.propTypes = {
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
    title: PropTypes.string,
    appId: PropTypes.string.isRequired,
    showMenuAssistant: PropTypes.bool,
};

JdashboardResolvedPage.defaultProps = {
    className: null,
    title: 'Jdashboard',
    showMenuAssistant: true,
};

export default JdashboardResolvedPage;