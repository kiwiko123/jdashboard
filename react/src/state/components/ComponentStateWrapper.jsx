import React from 'react';
import PropTypes from 'prop-types';

function resolveSuccessfully() {
    return true;
}

const ComponentStateWrapper = ({
    component, data, canResolve,
}) => {
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