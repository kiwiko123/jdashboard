import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import '../styles/Banner.css';

export const BANNER_TYPES = {
    info: 'info',
    warning: 'warning',
    danger: 'danger',
    success: 'success',
};

function getIconClassName(bannerType, iconSizeFactor) {
    let iconClassName = '';
    switch (bannerType) {
        case BANNER_TYPES.success:
            iconClassName = 'fas fa-check-circle';
            break;
        case BANNER_TYPES.danger:
            iconClassName = 'fas fa-exclamation-triangle';
            break;
        case BANNER_TYPES.warning:
            iconClassName = 'fas fa-bell';
            break;
        case BANNER_TYPES.info:
        default:
            iconClassName = 'fas fa-info-circle';
    }
    return `${iconClassName} fa-${iconSizeFactor}x`;
}

const Banner = ({
    type, iconSizeFactor, children, className,
}) => {
    const bannerClassName = classnames('Banner', className);
    const iconClassName = classnames('banner-icon', type, getIconClassName(type, iconSizeFactor));
    const icon = type && (<i className={iconClassName} />);

    return (
        <div className={bannerClassName}>
            {icon}
            {children}
        </div>
    );
};

Banner.propTypes = {
    type: PropTypes.oneOf([...Object.values(BANNER_TYPES), null]),
    iconSizeFactor: PropTypes.number,
    children: PropTypes.node,
    className: PropTypes.string,
};

Banner.defaultProps = {
    type: BANNER_TYPES.info,
    iconSizeFactor: 1,
    children: null,
    className: null,
};

export default Banner;