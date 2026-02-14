import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './GlassPane.css';
import './glass.css';

const GlassPane = ({
    children, className,
}) => {
    const divClassName = classnames('GlassPane', 'glass', className);

    return (
        <div className={divClassName}>
            {children}
        </div>
    );
};

GlassPane.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
};

GlassPane.defaultProps = {
    children: null,
    className: null,
};

export default GlassPane;