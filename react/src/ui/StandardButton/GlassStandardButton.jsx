import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import Button from 'react-bootstrap/Button';
import classnames from 'classnames';
import GlassPane from 'ui/styles/glass/GlassPane';
import { standardButtonProps, standardButtonDefaultProps } from './propTypes';

import './GlassStandardButton.css';

const SIZES = {
    "default": null,
    "small": "sm",
    "large": "lg",
};

const GlassStandardButton = ({
    children, variant, fontAwesomeClassName, disabled, block, onClick, className, size, type,
}) => {
    const [clickEffectClassName, setClickEffectClassName] = useState(null);
    const buttonClassName = classnames('StandardButton', {
        disabled,
    });
    const iconClassName = classnames('icon', fontAwesomeClassName);
    const icon = fontAwesomeClassName && (<i className={iconClassName} />);
    const containerClassName = classnames(
        'GlassStandardButton',
        'button',
        className,
        clickEffectClassName
    );

    const onClickHandler = useCallback(() => {
        if (onClick) {
            onClick();
        }
        setClickEffectClassName('highlighted');
        setTimeout(() => {
            setClickEffectClassName(null);
        }, 100);
    }, [onClick]);

    return (
        <GlassPane className={containerClassName}>
            <button
                className={buttonClassName}
                onClick={onClickHandler}
                disabled={disabled}
                type={type}
            >
                {icon}
                {children}
            </button>
        </GlassPane>
    );
};

GlassStandardButton.propTypes = standardButtonProps;
GlassStandardButton.defaultProps = standardButtonDefaultProps;

export default GlassStandardButton;