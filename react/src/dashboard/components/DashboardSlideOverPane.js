import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import IconButton from '../../common/components/IconButton';
import ClickTarget from '../../common/components/ClickTarget';
import { getWindowDimensions } from '../../common/js/window';

import './styles/DashboardSlideOverPane.css';

const DRAG_RESET_THRESHOLD_PX = 150;

function getOpenFromDirective(openFrom, currentX, currentY, screenX, screenY) {
    if (openFrom !== 'auto') {
        return openFrom;
    }

    const planeX = currentX <= screenX / 2 ? 'left' : 'right';
    const planeY = currentY <= screenY / 2 ? 'top' : 'bottom';
    return `${planeX} ${planeY}`;
}

const DashboardSlideOverPane = ({
    children, className, openFrom, expanded,
}) => {
    // Handle expanding/collapsing the pane.
    const [isExpanded, setIsExpanded] = useState(expanded);
    useEffect(() => {
        setIsExpanded(expanded);
    }, [expanded]);

    // Handle screen resizing events.
    const [dimensions, setDimensions] = useState(getWindowDimensions());
    useEffect(() => {
        const onResize = () => setDimensions(getWindowDimensions());
        window.addEventListener('resize', onResize);
        return () => window.removeEventListener('resize', onResize);
    }, [dimensions]);

    const [coordinates, setCoordinates] = useState({ x: 0, y: 0 });
        const { x, y } = coordinates;

    const openFromDirective = isExpanded
        ? getOpenFromDirective(openFrom, x, y, dimensions.width, dimensions.height)
        : null;
    const divClassName = classnames('DashboardSlideOverPane', className, openFromDirective, {
        open: isExpanded,
        closed: !isExpanded,
    });
    const iconClassName = isExpanded ? 'fas fa-times' : 'fas fa-cat';
    const buttonVariant = isExpanded ? 'light' : 'outline-light';
    const body = isExpanded && (
        <div className="body">
            {children}
        </div>
    );

    // When dragged to another part of the screen, stick the collapsed button there.
    const isNearDefaultCorner = x <= DRAG_RESET_THRESHOLD_PX && y <= DRAG_RESET_THRESHOLD_PX;
    const stickToDefaultCorner = isExpanded || isNearDefaultCorner;
    const positioningStyle = stickToDefaultCorner ? {} : {
        left: `${x}px`,
        top: `${y}px`,
    };

    return (
        <ClickTarget
            className={divClassName}
            draggable={!isExpanded}
            onDragEnd={event => setCoordinates({ x: event.pageX, y: event.pageY })}
            style={positioningStyle}
        >
            <div className="header">
                <IconButton
                    className="button-close"
                    fontAwesomeClassName={iconClassName}
                    variant={buttonVariant}
                    onClick={() => setIsExpanded(!isExpanded)}
                />
            </div>
            {body}
        </ClickTarget>
    );
};

DashboardSlideOverPane.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
    openFrom: PropTypes.oneOf(['top left', 'top right', 'bottom left', 'bottom right', 'auto']),
    expanded: PropTypes.bool,
};

DashboardSlideOverPane.defaultProps = {
    children: null,
    className: null,
    openFrom: 'top left',
    expanded: false,
};

export default DashboardSlideOverPane;