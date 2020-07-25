import React from 'react';
import PropTypes from 'prop-types';
import Button from 'react-bootstrap/Button';
import classnames from 'classnames';

import '../styles/IconButton.css';

const IconButton = ({
    children, variant, fontAwesomeClassName, disabled, block, onClick, className, size, type,
}) => {
    const buttonClassName = classnames('IconButton', className);
    const iconClassName = classnames('icon', fontAwesomeClassName, {
        'padded-right': Boolean(children),
    });
    const icon = fontAwesomeClassName && (<i className={iconClassName} />);

    return (
        <Button
            className={buttonClassName}
            variant={variant}
            onClick={onClick}
            disabled={disabled}
            block={block}
            size={size}
            type={type}
        >
            {icon}
            {children}
        </Button>
    );
};

IconButton.propTypes = {
    children: PropTypes.oneOfType([
        PropTypes.number,
        PropTypes.string,
        PropTypes.node,
    ]),

    // https://react-bootstrap.github.io/components/buttons
    variant: PropTypes.string,

    // https://fontawesome.com/icons?d=gallery&m=free
    fontAwesomeClassName: PropTypes.string,

    disabled: PropTypes.bool,
    block: PropTypes.bool,
    onClick: PropTypes.func,
    className: PropTypes.string,
    size: PropTypes.oneOf(["sm", "lg", null]),
    type: PropTypes.oneOf(['button', 'reset', 'submit', null]),
};

IconButton.defaultProps = {
    children: null,
    variant: null,
    fontAwesomeClassName: null,
    disabled: false,
    block: false,
    onClick: () => {},
    className: null,
    size: null,
    type: 'button',
};

export default IconButton;