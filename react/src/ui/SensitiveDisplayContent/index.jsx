import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './SensitiveDisplayContent.css';

function getIconClassName(isHidden) {
    const hiddenClassName = 'fas fa-eye-slash';
    const visibleClassName = 'fas fa-eye';
    return isHidden ? hiddenClassName : visibleClassName;
}

function getPlaceholder(placeholder, style) {
    if (placeholder) {
        return placeholder;
    }

    switch (style) {
        case 'password':
            // TODO FIXME this doesn't render anything currently
            return (
                <i
                    className="content-placeholder password"
                    type="password"
                    defaultValue="placeholder"
                    readOnly={true}
                />
            );
        default:
            return <div className="content-placeholder blurred" />;
    }
}

const SensitiveDisplayContent = ({
    className, children, placeholder, startHidden, style, theme,
}) => {
    const [isContentHidden, setIsContentHidden] = useState(startHidden);

    const divClassName = classnames('SensitiveDisplayContent', className);
    const contentForDisplay = isContentHidden
        ? getPlaceholder(placeholder, style)
        : children;

    return (
        <div
            className={divClassName}
            onClick={() => setIsContentHidden(!isContentHidden)}
        >
            {contentForDisplay}
            <i className={getIconClassName(isContentHidden)} />
        </div>
    );
};

SensitiveDisplayContent.propTypes = {
    className: PropTypes.string,
    children: PropTypes.node.isRequired,
    placeholder: PropTypes.node,
    startHidden: PropTypes.bool,
    style: PropTypes.oneOf(['blurred', 'password']),
    theme: PropTypes.oneOf(['light', 'dark']),
};

SensitiveDisplayContent.defaultProps = {
    className: null,
    placeholder: null,
    startHidden: true,
    style: 'blurred',
    theme: null,
}

export default SensitiveDisplayContent;