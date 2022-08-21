import React from 'react';
import PropTypes from 'prop-types';
import { useStateManager } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import JdashboardHeaderStateManager from '../header/JdashboardHeaderStateManager';
import IconButton from '../../common/components/IconButton';
import DashboardMenuSlideOverPane from './DashboardMenuSlideOverPane';

import './styles/DashboardHeader.css';

const DashboardHeader = (props) => {
    const headerStateManager = useStateManager(() => new JdashboardHeaderStateManager());
    const menuSlideOverPaneProps = {
        appId: props.appId,
        expanded: props.isMenuSlideOverExpanded,
        toggleExpand: props.toggleMenuSlideOver,
        accountProps: props.userData,
    };

    return (
       <div className="DashboardHeader">
           <div className="content">
               <ComponentStateManager
                   stateManager={headerStateManager}
                   component={DashboardMenuSlideOverPane}
                   staticProps={menuSlideOverPaneProps}
               />
               <IconButton
                   fontAwesomeClassName="fas fa-bars"
                   variant="outline-light"
                   onClick={props.toggleMenuSlideOver}
               />
               <h1 className="color-white">
                   {props.title}
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