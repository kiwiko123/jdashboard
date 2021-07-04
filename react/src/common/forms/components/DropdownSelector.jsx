import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './DropdownSelector.css';

const DropdownSelector = ({
    className, options, onSelect, preSelectedValue,
}) => {
    const [selectedValue, setSelectedValue] = useState(preSelectedValue);
    const select = useCallback((event) => {
        setSelectedValue(event.target.value);
        onSelect(event);
    }, [onSelect]);
    const divClassName = classnames('DropdownSelector', className);
    const optionElements = options.map(option => (
        <option
            key={option.value}
            value={option.value}
        >
            {option.label}
        </option>
    ));

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
};

export default DropdownSelector;