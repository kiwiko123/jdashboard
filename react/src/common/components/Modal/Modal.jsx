import React, { useCallback, useEffect } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { delay } from 'lodash';
import { useOnClickOutside } from '../../../state/hooks';

import './Modal.css';

function onPressEscape(event, callback) {
    if (event.key === 'Escape') {
        callback();
    }
}

const Modal = ({
    children, className, isOpen, size, closeOnBackgroundClick, close,
}) => {
    useEffect(() => {
        const closeOnEscape = event => onPressEscape(event, close);
        document.addEventListener('keydown', closeOnEscape);
        return () => {
            document.removeEventListener('keydown', closeOnEscape);
        };
    }, [close]);
    const modalRef = useOnClickOutside(() => {
        if (closeOnBackgroundClick && isOpen) {
            close();
        }
    });
    const divClassName = classnames('modal-container', {
        closed: !isOpen,
    });
    const modalClassName = classnames('Modal', size, className);

    return (
        <div className={divClassName}>
            <div
                className={modalClassName}
                ref={modalRef}
            >
                {children}
            </div>
        </div>
    );
};

Modal.propTypes = {
    children: PropTypes.node.isRequired,
    close: PropTypes.func.isRequired,
    closeOnBackgroundClick: PropTypes.bool,
    isOpen: PropTypes.bool,
    size: PropTypes.oneOf(['small', 'large', 'auto']),
    className: PropTypes.string,
};

Modal.defaultProps = {
    className: null,
    isOpen: true,
    size: 'auto',
    closeOnBackgroundClick: false,
};

export default Modal;