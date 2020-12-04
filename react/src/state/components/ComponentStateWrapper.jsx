import React, { cloneElement } from 'react';
import PropTypes from 'prop-types';
import { isEmpty } from 'lodash';

const ComponentStateWrapper = ({
    children, data, canResolve,
}) => {
    if (!canResolve()) {
        return null;
    }

    return isEmpty(data) ? children : cloneElement(children, data);
};

ComponentStateWrapper.propTypes = {
    children: PropTypes.node,
    data: PropTypes.object,
    canResolve: PropTypes.func,
};

ComponentStateWrapper.defaultProps = {
    children: null,
    data: null,
    canResolve: () => true,
};

export default ComponentStateWrapper;