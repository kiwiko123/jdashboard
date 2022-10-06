import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';
import VerticalNavigationBar from './VerticalNavigationBar';

import './index.css';

const MainViewWithSideNavBar = ({
    navigationItems, renderMainView, className, fullScreen,
}) => {
    const [selectedNavigationItemId, setSelectedNavigationItemId] = useState(get(navigationItems, [0, 'id'], null));
    const [mainView, setMainView] = useState(renderMainView({ id: selectedNavigationItemId }));

    const selectNavigationItem = useCallback(({ id }) => {
        if (id === selectedNavigationItemId) {
            return;
        }
        setSelectedNavigationItemId(id);
        const view = renderMainView({ id });
        setMainView(view);
    }, [renderMainView, selectedNavigationItemId]);

    const divClassName = classnames('MainViewWithSideNavBar', className, {
        'full-screen': fullScreen,
    });

    return (
        <div className={divClassName}>
            <VerticalNavigationBar
                className="left-nav-bar"
                items={navigationItems}
                onClick={selectNavigationItem}
                selectedItemId={selectedNavigationItemId}
            />
            <div className="main-view">
                {mainView}
            </div>
        </div>
    );
};

MainViewWithSideNavBar.propTypes = {
    navigationItems: PropTypes.arrayOf(PropTypes.shape({
         id: PropTypes.string.isRequired,
         label: PropTypes.string.isRequired,
     })).isRequired,
     renderMainView: PropTypes.func.isRequired,
     className: PropTypes.string,
     fullScreen: PropTypes.bool,
};

MainViewWithSideNavBar.defaultProps = {
    className: null,
    fullScreen: true,
};

export default MainViewWithSideNavBar;