import React, { cloneElement, useEffect, useReducer, useState } from 'react';
import PropTypes from 'prop-types';
import { isEmpty } from 'lodash';
import Broadcaster from '../Broadcaster';

let _global_id = 0;

const ComponentStateManager = ({
    children, broadcaster,
}) => {
    const [id] = useState(_global_id++);
    const [, forceUpdate] = useReducer(i => i + 1, 0);
    useEffect(() => {
        broadcaster._setUpdater(forceUpdate, id);
        return () => {
            broadcaster.removeUpdater(id);
        };
    }, [broadcaster, id]);

    const broadcasterState = broadcaster.getState();
    return isEmpty(broadcasterState) ? children : cloneElement(children, broadcasterState);
};

ComponentStateManager.propTypes = {
    children: PropTypes.element.isRequired,
    broadcaster: PropTypes.instanceOf(Broadcaster).isRequired,
};

export default ComponentStateManager;