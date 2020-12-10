import React from 'react';
import PropTypes from 'prop-types';
import logger from '../../common/js/logging';

function resolveSuccessfully() {
    return true;
}

const ComponentStateWrapper = ({
    component, data, canResolve,
}) => {
//     logger.debug(`Re-rendering ${component.name}`);
    return canResolve() && (
        <component {...data} />
    );
};

ComponentStateWrapper.propTypes = {
    component: PropTypes.elementType.isRequired,
    data: PropTypes.object,
    canResolve: PropTypes.func,
};

ComponentStateWrapper.defaultProps = {
    data: {},
    canResolve: resolveSuccessfully,
};

export default ComponentStateWrapper;