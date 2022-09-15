import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './InteractivePanelHint.css';

const ORIENTATION_ICONS_BY_NAME = {
    horizontal: 'fas fa-ellipsis-h',
    vertical: 'fas fa-ellipsis-v'
};

const InteractivePanelHint = ({
    className, orientation,
}) => {
    const divClassName = classnames('InteractivePanelHint', orientation, className);
    const iconClassName = `fas ${ORIENTATION_ICONS_BY_NAME[orientation]}`;
    return (
        <div className={divClassName}>
            <i className={iconClassName} />
        </div>
    );
};

InteractivePanelHint.propTypes = {
    orientation: PropTypes.oneOf(['horizontal', 'vertical']).isRequired,
    className: PropTypes.string,
};

InteractivePanelHint.defaultProps = {
    className: null,
};

export default InteractivePanelHint;