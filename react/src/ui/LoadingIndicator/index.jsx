import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { isNil } from 'lodash';

const LoadingIndicator = ({
    animated, size, className,
}) => {
    const divClassName = classnames('LoadingIndicator', className);
    const iconClassName = classnames('fas fa-circle-notch', {
        [`fa-${size}`]: !isNil(size) && size !== '1x',
        'fa-spin': animated,
    });
    return (
        <div className={divClassName}>
            <i className={iconClassName} />
        </div>
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