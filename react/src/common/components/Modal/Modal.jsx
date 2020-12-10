import React, { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { useOnClickOutside } from '../../../state/hooks';

import './Modal.css';

const Modal = ({
    children, className, isOpen, size,
}) => {
    const [shouldShow, setShouldShow] = useState(isOpen);
    useEffect(() => {
        setShouldShow(isOpen);
    }, [isOpen]);
    const modalRef = useOnClickOutside(() => {
        if (shouldShow) {
            setShouldShow(false);
        }
    });

    const divClassName = classnames('modal-container', className, {
        closed: !shouldShow,
    });
    const modalClassName = classnames('Modal', size);

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
    className: PropTypes.string,
    isOpen: PropTypes.bool,
    size: PropTypes.oneOf(['small', 'large', 'auto']),
};

Modal.defaultProps = {
    className: null,
    isOpen: true,
    size: 'auto',
};

export default Modal;