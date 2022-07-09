import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

const LoadingIndicator = ({
    animated, size, className,
}) => {
    const divClassName = classnames('LoadingIndicator', 'fas fa-circle-notch', className, `fa-${size}`, {
        'fa-spin': animated,
    });
    return (
        <i className={divClassName} />
    );
};

LoadingIndicator.propTypes = {
    animated: PropTypes.bool,
    size: PropTypes.oneOf(['1x', '2x', '4x']),
    className: PropTypes.string,
};

LoadingIndicator.defaultProps = {
    animated: true,
    size: '1x',
    className: null,
};

export default LoadingIndicator;