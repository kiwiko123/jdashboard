import React from 'react';
import PropTypes from 'prop-types';
import Button from 'react-bootstrap/Button';
import classnames from 'classnames';
import { standardButtonProps, standardButtonDefaultProps } from './propTypes';

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

StandardButton.propTypes = standardButtonProps;
StandardButton.defaultProps = standardButtonDefaultProps;

export default StandardButton;