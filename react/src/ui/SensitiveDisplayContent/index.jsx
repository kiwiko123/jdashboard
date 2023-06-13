import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './SensitiveDisplayContent.css';

function getIconClassName(isHidden, shouldReverse) {
    const hiddenClassName = 'fas fa-eye-slash';
    const visibleClassName = 'fas fa-eye';

    return isHidden
        ? (shouldReverse ? visibleClassName : hiddenClassName)
        : (shouldReverse ? hiddenClassName : visibleClassName);
}

const SensitiveDisplayContent = ({
    className, children, placeholder, startHidden, theme,
}) => {
    const [isContentHidden, setIsContentHidden] = useState(startHidden);
    const [isHovering, setIsHovering] = useState(false);
    const hover = useCallback(() => setIsHovering(true), []);
    const stopHover = useCallback(() => setIsHovering(false), []);

    const divClassName = classnames('SensitiveDisplayContent', className);
    const contentForDisplay = isContentHidden
        ? (placeholder || <div className="content-placeholder" />)
        : children;

    return (
        <div
            className={divClassName}
            onMouseEnter={hover}
            onMouseLeave={stopHover}
            onClick={() => setIsContentHidden(!isContentHidden)}
        >
            {contentForDisplay}
            <i className={getIconClassName(isContentHidden, isHovering)} />
        </div>
    );
};

SensitiveDisplayContent.propTypes = {
    className: PropTypes.string,
    children: PropTypes.node.isRequired,
    placeholder: PropTypes.node,
    startHidden: PropTypes.bool,
    theme: PropTypes.oneOf(['light', 'dark']),
};

SensitiveDisplayContent.defaultProps = {
    className: null,
    placeholder: null,
    theme: null,
}

export default SensitiveDisplayContent;