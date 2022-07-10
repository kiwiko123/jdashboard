import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './index.css';

const DEFAULT_COORDINATES = { x: 0, y: 0 };
const PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT = 50;
const PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH = 150;
const CLICK_START_INTERACTIVE_PIXELS_THRESHOLD = 400;

const DownArrow = () => (<i className="fas fa-arrow-down interactive-panel-indicator" />);
const UpArrow = () => (<i className="fas fa-arrow-up interactive-panel-indicator" />);
const RightArrow = () => (<i className="fas fa-arrow-right interactive-panel-indicator" />);
const LeftArrow = () => (<i className="fas fa-arrow-left interactive-panel-indicator" />);

const VerticalPanelHint = ({ location }) => (
    <div className={classnames('interactive-panel-hint', location)}>
        <i className="fas fa-ellipsis-h"></i>
    </div>
);
const HorizontalPanelHint = ({ location }) => (
    <div className={classnames('interactive-panel-hint', location)}>
        <i className="fas fa-ellipsis-v"></i>
    </div>
);

const PerimeterPullView = ({
    children, className, size, onPullFromTop, onPullFromBottom, onPullFromLeft, onPullFromRight,
    interactiveHeader, interactiveFooter, interactiveLeftPane, interactiveRightPane,
}) => {
    const [isClicking, setIsClicking] = useState(false);
    const [pointerCoordinates, setPointerCoordinates] = useState(DEFAULT_COORDINATES);
    const [clickStartCoordinates, setClickStartCoordinates] = useState(DEFAULT_COORDINATES);
    const [showInteractiveHeader, setShowInteractiveHeader] = useState(false);
    const [showInteractiveFooter, setShowInteractiveFooter] = useState(false);
    const [showInteractiveLeftPane, setShowInteractiveLeftPane] = useState(false);
    const [showInteractiveRightPane, setShowInteractiveRightPane] = useState(false);

    const canUseInteractiveHeader = Boolean(onPullFromTop);
    const canUseInteractiveFooter = Boolean(onPullFromBottom);
    const canUseInteractiveLeftPane = Boolean(onPullFromLeft);
    const canUseInteractiveRightPane = Boolean(onPullFromRight);

    const allowInteractiveHeader = canUseInteractiveHeader
        && isClicking
        && clickStartCoordinates.y <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.y > clickStartCoordinates.y;

    const allowInteractiveFooter = canUseInteractiveFooter
        && isClicking
        && (window.innerHeight - clickStartCoordinates.y) <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.y < clickStartCoordinates.y;

    const allowInteractiveLeftPane = canUseInteractiveLeftPane
        && isClicking
        && clickStartCoordinates.x <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.x > clickStartCoordinates.x;

    const allowInteractiveRightPane = canUseInteractiveRightPane
        && isClicking
        && (window.innerWidth - clickStartCoordinates.x) <= CLICK_START_INTERACTIVE_PIXELS_THRESHOLD
        && pointerCoordinates.x < clickStartCoordinates.x;

    useEffect(
        () => {
            setShowInteractiveHeader(allowInteractiveHeader && !(showInteractiveFooter || showInteractiveLeftPane || showInteractiveRightPane));
            setShowInteractiveFooter(allowInteractiveFooter && !(showInteractiveHeader || showInteractiveLeftPane || showInteractiveRightPane));
            setShowInteractiveLeftPane(allowInteractiveLeftPane && !(showInteractiveHeader || showInteractiveFooter || showInteractiveRightPane));
            setShowInteractiveRightPane(allowInteractiveRightPane && !(showInteractiveHeader || showInteractiveFooter || showInteractiveLeftPane));
        },
        [
            allowInteractiveHeader, allowInteractiveFooter, allowInteractiveLeftPane, allowInteractiveRightPane,
            showInteractiveHeader, showInteractiveFooter, showInteractiveLeftPane, showInteractiveRightPane,
        ]);

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

    const onClickEnd = useCallback(
        () => {
            setIsClicking(false);

            if (showInteractiveHeader && pointerCoordinates.y - clickStartCoordinates.y >= PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT) {
                onPullFromTop();
            }

            if (showInteractiveFooter && clickStartCoordinates.y - pointerCoordinates.y >= PULL_TO_VIEW_INTERACTIVE_PIXELS_HEIGHT) {
                onPullFromBottom();
            }

            if (showInteractiveLeftPane && pointerCoordinates.x - clickStartCoordinates.x >= PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH) {
                onPullFromLeft();
            }

            if (showInteractiveRightPane && clickStartCoordinates.x - pointerCoordinates.x >= PULL_TO_VIEW_INTERACTIVE_PIXELS_WIDTH) {
                onPullFromRight();
            }
        },
        [
            pointerCoordinates, clickStartCoordinates, onPullFromTop, onPullFromBottom, onPullFromLeft, onPullFromRight,
            showInteractiveHeader, showInteractiveFooter, showInteractiveLeftPane, showInteractiveRightPane,
        ],
    );

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
    } else if (canUseInteractiveHeader) {
        interactiveHeaderDiv = <VerticalPanelHint location="top" />
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
    } else if (canUseInteractiveFooter) {
        interactiveFooterDiv = <VerticalPanelHint location="bottom" />;
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
    } else if (canUseInteractiveLeftPane) {
        interactiveLeftPaneDiv = <HorizontalPanelHint location="left" />;
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
    } else if (canUseInteractiveRightPane) {
        interactiveRightPaneDiv = <HorizontalPanelHint location="right" />;
    }

    const divClassName = classnames('PerimeterPullView', className, {
        fullscreen: size === 'full-screen',
    });
    return (
        <div
            className={divClassName}
            onMouseMove={onMouseMove}
        >
            {interactiveHeaderDiv}
            <div className="pull-view-body">
                {interactiveLeftPaneDiv}
                <div className="pull-view-body-content">
                    {children}
                </div>
                {interactiveRightPaneDiv}
            </div>
            {interactiveFooterDiv}
        </div>
    );
};

PerimeterPullView.propTypes = {
    children: PropTypes.node,
    onPullFromTop: PropTypes.func,
    onPullFromBottom: PropTypes.func,
    onPullFromLeft: PropTypes.func,
    onPullFromRight: PropTypes.func,
    interactiveHeader: PropTypes.node,
    interactiveFooter: PropTypes.node,
    interactiveLeftPane: PropTypes.node,
    interactiveRightPane: PropTypes.node,
    className: PropTypes.string,
    size: PropTypes.oneOf([null, 'full-screen']),
};

PerimeterPullView.defaultProps = {
    children: null,
    onPullFromTop: null,
    onPullFromBottom: null,
    onPullFromLeft: null,
    onPullFromRight: null,
    interactiveHeader: <DownArrow />,
    interactiveFooter: <UpArrow />,
    interactiveLeftPane: <RightArrow />,
    interactiveRightPane: <LeftArrow />,
    className: null,
    size: 'full-screen',
};

export default PerimeterPullView;