import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';

import './StatusPlane.css';

const StatusPlane = ({
    messages,
}) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [isHovering, setIsHovering] = useState(false);
    const [incomingMessages, setIncomingMessages] = useState([]);
    const [currentIncomingMessage, setCurrentIncomingMessage] = useState(null);

    useEffect(() => {
        if (messages.length <= 0) {
            return;
        }

        const knownMessageIds = new Set(incomingMessages.map(message => message.id));
        const newMessages = messages.filter(message => !knownMessageIds.has(message.id));
        if (newMessages.length > 0) {
            setIncomingMessages([
                ...incomingMessages,
                ...newMessages,
            ]);

            if (!currentIncomingMessage) {
                setCurrentIncomingMessage(newMessages[0]);
            }
        }
    }, [messages]);

    useEffect(() => {
        if (!currentIncomingMessage || incomingMessages.length <= 0) {
            return;
        }

        setTimeout(() => {
            const remainingMessages = incomingMessages.filter(message => message.id !== currentIncomingMessage.id);
            setIncomingMessages(remainingMessages);
            setCurrentIncomingMessage(remainingMessages.length >= 0 ? remainingMessages[0] : null);
        }, 4000);
    }, [currentIncomingMessage]);

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
            const introIconClassName = classnames('intro-icon', {
                'fas fa-circle': !isHovering,
                'fas fa-expand-alt': isHovering,
            });
            const introIcon = <i className={introIconClassName} />;
            collapsedViewContent = (
                <div className="icons">
                    <i className={introIconClassName} />
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