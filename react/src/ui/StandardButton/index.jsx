import React from 'react';
import PropTypes from 'prop-types';
import Button from 'react-bootstrap/Button';
import classnames from 'classnames';

import './StandardButton.css';

const SIZES = {
    "default": null,
    "small": "sm",
    "large": "lg",
};

const StandardButton = ({
    children, variant, fontAwesomeClassName, disabled, block, onClick, className, size, type,
}) => {
    const buttonClassName = classnames('StandardButton', className);
    const iconClassName = classnames('icon', fontAwesomeClassName);
    const icon = fontAwesomeClassName && (<i className={iconClassName} />);

    return (
        <Button
            className={buttonClassName}
            variant={variant}
            onClick={onClick}
            disabled={disabled}
            block={block}
            size={SIZES[size]}
            type={type}
        >
            {icon}
            {children}
        </Button>
    );
};

StandardButton.propTypes = {
    children: PropTypes.string.isRequired,

    // https://react-bootstrap.github.io/components/buttons
    variant: PropTypes.oneOf([
        'primary', 'secondary', 'success', 'warning', 'danger', 'info', 'light', 'dark', 'link',
        'outline-primary', 'outline-secondary', 'outline-success', 'outline-warning', 'outline-danger', 'outline-info',
        'outline-light', 'outline-dark', 'outline-link']),

    // https://fontawesome.com/v5/search?m=free
    fontAwesomeClassName: PropTypes.string,

    disabled: PropTypes.bool,
    block: PropTypes.bool,
    onClick: PropTypes.func,
    className: PropTypes.string,
    size: PropTypes.oneOf(["default", "small", "large"]),
    type: PropTypes.oneOf(['button', 'reset', 'submit']),
};

StandardButton.defaultProps = {
    variant: null,
    fontAwesomeClassName: null,
    disabled: false,
    block: false,
    onClick: null,
    className: null,
    size: "default",
    type: 'button',
};

export default StandardButton;