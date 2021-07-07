import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { isNil } from 'lodash';

import './DropdownSelector.css';

const DEFAULT_SELECTED_VALUE = 'Select...';

const DropdownSelector = ({
    className, options, onSelect, preSelectedValue,
}) => {
    const [selectedValue, setSelectedValue] = useState(preSelectedValue);
    useEffect(() => {
        if (isNil(preSelectedValue)) {
            setSelectedValue(DEFAULT_SELECTED_VALUE);
        }
    }, [preSelectedValue]);

    const select = useCallback((event) => {
        setSelectedValue(event.target.value);
        onSelect(event);
    }, [onSelect]);
    const divClassName = classnames('DropdownSelector', className);

    let optionElements = [];
    if (selectedValue === DEFAULT_SELECTED_VALUE) {
        optionElements.push(<option key="__default" value="__default">{DEFAULT_SELECTED_VALUE}</option>);
    }
    options.map(option => (
        <option
            key={option.value}
            value={option.value}
        >
            {option.label}
        </option>
    ))
        .forEach(option => optionElements.push(option));

    return (
        <div className={divClassName}>
            <select
                value={selectedValue}
                onChange={select}
            >
                {optionElements}
            </select>
        </div>
    );
};

DropdownSelector.propTypes = {
    className: PropTypes.string,
    options: PropTypes.arrayOf(PropTypes.shape({
        label: PropTypes.string.isRequired,
        value: PropTypes.any.isRequired,
    })).isRequired,
    onSelect: PropTypes.func.isRequired,
    preSelectedValue: PropTypes.any,
};

DropdownSelector.defaultProps = {
    className: null,
    preSelectedValue: 'Select...'
};

export default DropdownSelector;