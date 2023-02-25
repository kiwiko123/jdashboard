import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './VerticalNavigationBar.css';

const VerticalNavigationBar = ({
    className, items, onClick, selectedItemId,
}) => {
    const navigationItems = items.map((item) => {
        const selectItem = () => {
            onClick({ id: item.id });
        };
        const itemClassName = classnames('nav-item', item.className, {
            selected: item.id === selectedItemId,
        });
        return (
            <div
                className={itemClassName}
                onClick={selectItem}
                key={item.id}
            >
                {item.content}
            </div>
        );
    });

    const divClassName = classnames('VerticalNavigationBar', className);
    return (
        <div className={divClassName}>
            {navigationItems}
        </div>
    );
};

VerticalNavigationBar.propTypes = {
    className: PropTypes.string,
    items: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.string.isRequired,
        content: PropTypes.node.isRequired,
        className: PropTypes.string,
    })),
    onClick: PropTypes.func,
    selectedItemId: PropTypes.string,
};

VerticalNavigationBar.defaultProps = {
    className: null,
    items: [],
    onClick: () => {},
    selectedItemId: null,
};

export default VerticalNavigationBar;