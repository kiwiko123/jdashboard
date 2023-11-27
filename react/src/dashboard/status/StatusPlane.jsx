import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './StatusPlane.css';

const StatusPlane = ({
    messages,
}) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [isHovering, setIsHovering] = useState(false);
    const [currentIncomingMessage, setCurrentIncomingMessage] = useState(null);
    const currentIncomingMessageTimeoutIdRef = useRef(null);

    useEffect(() => {
        if (messages.length <= 0) {
            return;
        }

        const newestMessage = messages[0];
        if (currentIncomingMessage && (currentIncomingMessage.id === newestMessage.id)) {
            return;
        }

        setCurrentIncomingMessage(newestMessage);
        if (currentIncomingMessageTimeoutIdRef.current) {
            clearTimeout(currentIncomingMessageTimeoutIdRef.current);
            currentIncomingMessageTimeoutIdRef.current = null;
        }

        currentIncomingMessageTimeoutIdRef.current = setTimeout(() => {
            setCurrentIncomingMessage(null);
            currentIncomingMessageTimeoutIdRef.current = null;
        }, 4000);
    }, [messages]);

    let collapsedView;
    const shouldShowCollapsedView = !isExpanded;
    if (shouldShowCollapsedView) {
        let collapsedViewContent;

        if (currentIncomingMessage) {
            const messageIconClassName = currentIncomingMessage.iconClassName || 'fas fa-bullhorn';
            collapsedViewContent = (
                <div className="incoming-message">
                    <div className="message-icon">
                        <i className={messageIconClassName} />
                    </div>
                    <div className="message">
                        {currentIncomingMessage.message}
                    </div>
                </div>
            );
        } else {
            let unreadMessagesArea;
            const hasUnreadMessages = messages.length > 0;
            if (hasUnreadMessages) {
                unreadMessagesArea = (
                    <div className="unread-message-notify">
                        <i className="unread-message-icon fas fa-bell" />
                        <span className="count">{messages.length}</span>
                    </div>
                );
            }

            const introIconClassName = classnames('intro-icon', {
                'fas fa-circle': !hasUnreadMessages && !isHovering,
                'fas fa-expand-alt': isHovering || hasUnreadMessages,
            });
            collapsedViewContent = (
                <div className="icons">
                    {unreadMessagesArea}
                    <div className="intro">
                        <i className={introIconClassName} />
                    </div>
                </div>
            );
        }

        collapsedView = (
            <div className="collapsed-view">
                {collapsedViewContent}
            </div>
        );
    }

    let expandedView;
    const shouldShowExpandedView = isExpanded;
    if (shouldShowExpandedView) {
        expandedView = (
            <div className="expanded-view content">
                Hello! Expanded!
            </div>
        );
    }

    const divClassName = classnames('StatusPlane', {
        collapsed: shouldShowCollapsedView,
        expanded: shouldShowExpandedView,
        'incoming-message': Boolean(currentIncomingMessage),
        'unread-messages': messages.length > 0,
    });

    return (
        <div
            onClick={() => setIsExpanded(!isExpanded)}
            onMouseEnter={() => setIsHovering(true)}
            onMouseLeave={() => setIsHovering(false)}
            className={divClassName}
        >
            {collapsedView}
            {expandedView}
        </div>
    );
};

StatusPlane.propTypes = {
    messages: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.string.isRequired,
        iconClassName: PropTypes.string,
        message: PropTypes.string.isRequired,
    })),
};

StatusPlane.defaultProps = {
    messages: [],
};

export default StatusPlane;