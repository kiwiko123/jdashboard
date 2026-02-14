import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './SlideOverPane.css';

const StandardSlideOverPane = ({
    children, className, expanded, openFrom,
}) => {
    const [isExpanded, setIsExpanded] = useState(expanded);

    // `isExpanded` (state) is the source-of-truth.
    // If `expanded` (prop) changes, update the source-of-truth.
    useEffect(() => {
        setIsExpanded(expanded);
    }, [expanded]);

    const openFromDirective = `from-${openFrom}`;
    const divClassName = classnames('SlideOverPane', className, openFromDirective, {
        open: isExpanded,
        closed: !isExpanded,
    });
    return (
        <div className={divClassName}>
            {isExpanded && children}
        </div>
    );
};

StandardSlideOverPane.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
    expanded: PropTypes.bool,
    openFrom: PropTypes.oneOf(['side', 'top', 'bottom']),
};

StandardSlideOverPane.defaultProps = {
    children: null,
    className: null,
    expanded: true,
    openFrom: 'side',
};

export default StandardSlideOverPane;