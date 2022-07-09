import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './index.css';

const DEFAULT_COORDINATES = { x: 0, y: 0 };
const PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT = 50;
const PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH = 150;
const CLICK_START_INTERACTIVE_PIXELS_THRESHOLD = 400;

const DefaultInteractiveHeaderContent = () => (<i className="fas fa-arrow-down interactive-panel-indicator" />);
const DefaultInteractiveFooterContent = () => (<i className="fas fa-arrow-up interactive-panel-indicator" />);
const DefaultInteractiveLeftPaneContent = () => (<i className="fas fa-arrow-right interactive-panel-indicator" />);
const DefaultInteractiveRightPaneContent = () => (<i className="fas fa-arrow-left interactive-panel-indicator" />);

const SwipeView = ({
    children, className, size, onSwipeFromTop, onSwipeFromBottom, onSwipeFromLeft, onSwipeFromRight,
    interactiveHeader, interactiveFooter, interactiveLeftPane, interactiveRightPane,
}) => {
    const [isClicking, setIsClicking] = useState(false);
    const [pointerCoordinates, setPointerCoordinates] = useState(DEFAULT_COORDINATES);
    const [clickStartCoordinates, setClickStartCoordinates] = useState(DEFAULT_COORDINATES);
    const [showInteractiveHeader, setShowInteractiveHeader] = useState(false);
    const [showInteractiveFooter, setShowInteractiveFooter] = useState(false);
    const [showInteractiveLeftPane, setShowInteractiveLeftPane] = useState(false);
    const [showInteractiveRightPane, setShowInteractiveRightPane] = useState(false);

    const allowInteractiveHeader = onSwipeFromTop
        && isClicking
        && clickStartCoordinates.y <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.y > clickStartCoordinates.y;

    const allowInteractiveFooter = onSwipeFromBottom
        && isClicking
        && (window.innerHeight - clickStartCoordinates.y) <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.y < clickStartCoordinates.y;

    const allowInteractiveLeftPane = onSwipeFromLeft
        && isClicking
        && clickStartCoordinates.x <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.x > clickStartCoordinates.x;

    const allowInteractiveRightPane = onSwipeFromRight
        && isClicking
        && (window.innerWidth - clickStartCoordinates.x) <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.x < clickStartCoordinates.x;

    useEffect(() => {
        setShowInteractiveHeader(allowInteractiveHeader && !(showInteractiveFooter || showInteractiveLeftPane || showInteractiveRightPane));
        setShowInteractiveFooter(allowInteractiveFooter && !(showInteractiveHeader || showInteractiveLeftPane || showInteractiveRightPane));
        setShowInteractiveLeftPane(allowInteractiveLeftPane && !(showInteractiveHeader || showInteractiveFooter || showInteractiveRightPane));
        setShowInteractiveRightPane(allowInteractiveRightPane && !(showInteractiveHeader || showInteractiveFooter || showInteractiveLeftPane));
    }, [allowInteractiveHeader, allowInteractiveFooter, allowInteractiveLeftPane, allowInteractiveRightPane]);

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

        if (showInteractiveHeader && pointerCoordinates.y - clickStartCoordinates.y >= PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT) {
            onSwipeFromTop();
        }

        if (showInteractiveFooter && clickStartCoordinates.y - pointerCoordinates.y >= PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT) {
            onSwipeFromBottom();
        }

        if (showInteractiveLeftPane && pointerCoordinates.x - clickStartCoordinates.x >= PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH) {
            onSwipeFromLeft();
        }

        if (showInteractiveRightPane && clickStartCoordinates.x - pointerCoordinates.x >= PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH) {
            onSwipeFromRight();
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

    let interactiveHeaderDiv;
    if (showInteractiveHeader) {
        const height = Math.min(pointerCoordinates.y - clickStartCoordinates.y, PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT);
        const style = { height };
        interactiveHeaderDiv = (
            <div className="interactive-panel interactive-header" style={style}>
                {interactiveHeader}
            </div>
        );
    }

    let interactiveFooterDiv;
    if (showInteractiveFooter) {
        const height = Math.min(clickStartCoordinates.y - pointerCoordinates.y, PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT);
        const style = { height };
        interactiveFooterDiv = (
            <div className="interactive-panel interactive-footer" style={style}>
                {interactiveFooter}
            </div>
        );
    }

    let interactiveLeftPaneDiv;
    if (showInteractiveLeftPane) {
        const width = Math.min(pointerCoordinates.x - clickStartCoordinates.x, PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH);
        const style = { width };
        interactiveLeftPaneDiv = (
            <div className="interactive-panel interactive-left-pane" style={style}>
                {interactiveLeftPane}
            </div>
        );
    }

    let interactiveRightPaneDiv;
    if (showInteractiveRightPane) {
        const width = Math.min(clickStartCoordinates.x - pointerCoordinates.x, PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH);
        const style = { width };
        interactiveRightPaneDiv = (
            <div className="interactive-panel interactive-right-pane" style={style}>
                {interactiveRightPane}
            </div>
        );
    }

    const divClassName = classnames('SwipeView', className, {
        fullscreen: size === 'full-screen',
    });
    return (
        <div
            className={divClassName}
            onMouseMove={onMouseMove}
        >
            {interactiveHeaderDiv}
            <div className="swipe-view-body">
                {interactiveLeftPaneDiv}
                <div className="swipe-view-body-content">
                    {children}
                </div>
                {interactiveRightPaneDiv}
            </div>
            {interactiveFooterDiv}
        </div>
    );
};

SwipeView.propTypes = {
    children: PropTypes.node,
    onSwipeFromTop: PropTypes.func,
    onSwipeFromBottom: PropTypes.func,
    onSwipeFromLeft: PropTypes.func,
    onSwipeFromRight: PropTypes.func,
    interactiveHeader: PropTypes.node,
    interactiveFooter: PropTypes.node,
    interactiveLeftPane: PropTypes.node,
    interactiveRightPane: PropTypes.node,
    className: PropTypes.string,
    size: PropTypes.oneOf([null, 'full-screen']),
};

SwipeView.defaultProps = {
    children: null,
    onSwipeFromTop: null,
    onSwipeFromBottom: null,
    onSwipeFromLeft: null,
    onSwipeFromRight: null,
    interactiveHeader: <DefaultInteractiveHeaderContent />,
    interactiveFooter: <DefaultInteractiveFooterContent />,
    interactiveLeftPane: <DefaultInteractiveLeftPaneContent />,
    interactiveRightPane: <DefaultInteractiveRightPaneContent />,
    className: null,
    size: 'full-screen',
};

export default SwipeView;