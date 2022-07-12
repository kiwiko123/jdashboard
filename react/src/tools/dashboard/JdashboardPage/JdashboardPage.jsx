import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager, useTabTitle } from 'state/hooks';
import DashboardHeaderStateManager from 'dashboard/state/DashboardHeaderStateManager';
import DashboardHeader from 'dashboard/components/DashboardHeader';
import JdashboardPageView from './JdashboardPageView';
import conditionsPropType from '../conditions/conditionsPropType';
import DashboardMenuAssistantPane from 'dashboard/components/DashboardMenuAssistantPane';
import useManagedClientSession from './util/useManagedClientSession';
import useRequiredConditions from './util/useRequiredConditions';

import 'common/styles/colors.css';
import 'common/styles/common.css';
import './JdashboardPage.css';

const Div = ({ className, children }) => <div className={className}>{children}</div>;

const JdashboardPage = ({
    children, className, title, appId, showMenuAssistant, enableInteractivePerimeter,
}) => {
    useTabTitle(title);
    useManagedClientSession();
    const headerStateManager = useStateManager(() => new DashboardHeaderStateManager());

    const pageClassName = classnames('JdashboardPage', className);
    const menuAssistant = showMenuAssistant && (
        <DashboardMenuAssistantPane
            openFrom="auto"
            expanded={false}
        >
        </DashboardMenuAssistantPane>
    );
    const ViewComponent = enableInteractivePerimeter ? JdashboardPageView : Div;

    return (
        <ViewComponent className={pageClassName}>
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
        </ViewComponent>
    );
}

JdashboardPage.propTypes = {
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
    title: PropTypes.string,
    appId: PropTypes.string.isRequired,
    showMenuAssistant: PropTypes.bool,
    enableInteractivePerimeter: PropTypes.bool,
};

JdashboardPage.defaultProps = {
    className: null,
    title: 'Jdashboard',
    showMenuAssistant: true,
    enableInteractivePerimeter: true,
};

export default JdashboardPage;