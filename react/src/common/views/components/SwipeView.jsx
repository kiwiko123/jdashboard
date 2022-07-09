import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import logger from 'tools/monitoring/logging';

import './SwipeView.css';

const DEFAULT_COORDINATES = { x: 0, y: 0 };
const PULL_TO_VIEW_INTERACTIVE_PIXELS_DELTA = 50;
const CLICK_START_INTERACTIVE_PIXELS_THRESHOLD = 400;

const SwipeView = ({
    children, className, onSwipeFromTop, onSwipeFromBottom, onSwipeFromLeft, onSwipeFromRight,
}) => {
    const [isClicking, setIsClicking] = useState(false);
    const [pointerCoordinates, setPointerCoordinates] = useState(DEFAULT_COORDINATES);
    const [clickStartCoordinates, setClickStartCoordinates] = useState(DEFAULT_COORDINATES);

    const onMouseMove = useCallback((event) => {
        if (!isClicking) {
            return;
        }
        setPointerCoordinates({ x: event.screenX, y: event.screenY });
    }, [isClicking]);

    const onClickStart = useCallback((event) => {
        setIsClicking(true);
        setClickStartCoordinates({ x: event.screenX, y: event.screenY });
        setPointerCoordinates({ x: event.screenX, y: event.screenY });
    }, []);

    const onClickEnd = useCallback(() => {
        setIsClicking(false);

        if (onSwipeFromTop && pointerCoordinates.y - clickStartCoordinates.y >= PULL_TO_VIEW_INTERACTIVE_PIXELS_DELTA) {
            onSwipeFromTop();
        }
    }, [pointerCoordinates, clickStartCoordinates]);

    useEffect(() => {
        document.addEventListener('mousedown', onClickStart);
        document.addEventListener('mouseup', onClickEnd);

        return () => {
            document.removeEventListener('mousedown', onClickStart);
            document.removeEventListener('mouseup', onClickEnd);
        };
    }, [onClickStart, onClickEnd]);

    let interactiveHeader;
    if (isClicking
        && clickStartCoordinates.y <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.y > clickStartCoordinates.y) {
        const height = Math.min(pointerCoordinates.y - clickStartCoordinates.y, PULL_TO_VIEW_INTERACTIVE_PIXELS_DELTA);
        const style = { height };
        interactiveHeader = (
            <div className="interactive-header" style={style}>
                Interactive Header!
            </div>
        );
    }

    let interactiveFooter;
    if (isClicking
        && (window.innerHeight - clickStartCoordinates.y) <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.y < clickStartCoordinates.y) {
        const height = Math.min(clickStartCoordinates.y - pointerCoordinates.y, PULL_TO_VIEW_INTERACTIVE_PIXELS_DELTA);
        const style = { height };
        interactiveFooter = (
            <div className="interactive-footer" style={style}>
                Interactive Footer!
            </div>
        );
    }

    const divClassName = classnames('SwipeView', className);
    return (
        <div
            className={divClassName}
            onMouseMove={onMouseMove}
        >
            {interactiveHeader}
            <div className="swipe-view-body">
                <div className="swipe-view-body-content">
                    {children}
                </div>
            </div>
            {interactiveFooter}
        </div>
    );
};

SwipeView.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
    onSwipeFromTop: PropTypes.func,
    onSwipeFromBottom: PropTypes.func,
    onSwipeFromLeft: PropTypes.func,
    onSwipeFromRight: PropTypes.func,
};

SwipeView.defaultProps = {
    children: null,
    className: null,
    onSwipeFromTop: null,
    onSwipeFromBottom: null,
    onSwipeFromLeft: null,
    onSwipeFromRight: null,
};

export default SwipeView;