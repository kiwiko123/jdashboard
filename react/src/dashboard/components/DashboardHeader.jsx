import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { useOnClickOutside, useStateManager } from 'state/hooks';
import ComponentStateManager from 'state/components/ComponentStateManager';
import StandardButton from 'ui/StandardButton';
import JdashboardHeaderStateManager from '../header/JdashboardHeaderStateManager';
import IconButton from '../../common/components/IconButton';
import DashboardMenuSlideOverPane from './DashboardMenuSlideOverPane';
import SystemSettingsPane from './SystemSettingsPane';
import SlideOverPane from '../../common/components/SlideOverPane';

import './styles/DashboardHeader.css';

const DashboardHeader = (props) => {
    const headerStateManager = useStateManager(() => new JdashboardHeaderStateManager());
    const [isSystemSettingsPaneExpanded, setIsSystemSettingsPaneExpanded] = useState(false);
    const systemSettingsPaneRef = useOnClickOutside(() => {
        if (isSystemSettingsPaneExpanded) {
            setIsSystemSettingsPaneExpanded(false);
        }
    });
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
               <StandardButton
                   fontAwesomeClassName="fas fa-bars"
                   onClick={props.toggleMenuSlideOver}
               />
               <h1 className="color-white">
                   {props.title}
               </h1>
               <div className="section-system-settings">
                   <StandardButton
                      className="system-settings clear"
                      fontAwesomeClassName="fas fa-cog"
                      onClick={() => setIsSystemSettingsPaneExpanded(!isSystemSettingsPaneExpanded)}
                  />
                  <SlideOverPane
                      className="system-settings-pane immersive"
                      ref={systemSettingsPaneRef}
                      expanded={isSystemSettingsPaneExpanded}
                      openFrom="top"
                  >
                      <SystemSettingsPane />
                  </SlideOverPane>
               </div>
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