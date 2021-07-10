import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import IconButton from 'common/components/IconButton';

import './ToggleSwitch.css';

const ToggleSwitch = ({
    className, isSelected, onToggle,
}) => {
    const [isOn, setIsOn] = useState(isSelected);
    useEffect(() => {
        setIsOn(isSelected);
    }, [isSelected]);
    const onClick = useCallback((event) => {
        const newValue = !isOn;
        setIsOn(newValue);
        onToggle(event, newValue);
    }, [onToggle, isOn]);
    const divClassName = classnames('ToggleSwitch', className);
    const iconClassName = isOn ? 'fas fa-toggle-on' : 'fas fa-toggle-off';

    return (
        <IconButton
            className={divClassName}
            fontAwesomeClassName={iconClassName}
            onClick={onClick}
        />
    );
};

ToggleSwitch.propTypes = {
    className: PropTypes.string,
    isSelected: PropTypes.bool,
    onToggle: PropTypes.func,
};

ToggleSwitch.defaultProps = {
    className: null,
    isSelected: false,
    onToggle: () => {},
};

export default ToggleSwitch;