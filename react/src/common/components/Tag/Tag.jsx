import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './Tag.css';

const Tag = ({
    children, className, remove,
}) => {
    const divClassName = classnames('Tag', className);
    const removeButton = remove && (
        <i
            className="fas fa-times remove-tag-button"
            onClick={remove}
        />
    );
    return (
        <div className={divClassName}>
            {children}
            {removeButton}
        </div>
    );
};

Tag.propTypes = {
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
    remove: PropTypes.func,
};

Tag.defaultProps = {
    className: null,
    remove: null,
};

export default Tag;