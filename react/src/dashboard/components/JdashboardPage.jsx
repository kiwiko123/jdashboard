import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager, useTabTitle } from 'state/hooks';
import useManagedClientSession from './util/useManagedClientSession';
import useRequiredConditions from './util/useRequiredConditions';
import conditionsPropType from 'tools/dashboard/conditions/conditionsPropType';
import DashboardHeaderStateManager from '../../dashboard/state/DashboardHeaderStateManager';
import DashboardHeader from './DashboardHeader';
import DashboardMenuAssistantPane from './DashboardMenuAssistantPane';

import '../../common/styles/colors.css';
import '../../common/styles/common.css';
import './styles/DashboardPage.css';

const JdashboardPage = ({
    children, className, title, appId, showMenuAssistant, requiredConditions,
}) => {
    useRequiredConditions(requiredConditions);
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

    // If any of the conditions resolve to a falsy value, the page will be redirected to `/not-found`.
    requiredConditions: PropTypes.arrayOf(conditionsPropType),
};

JdashboardPage.defaultProps = {
    className: null,
    title: 'Jdashboard',
    showMenuAssistant: true,
    requiredConditions: [],
};

export default JdashboardPage;