import React, { PureComponent, cloneElement } from 'react';
import PropTypes from 'prop-types';
import { isEmpty } from 'lodash';
import Broadcaster from '../Broadcaster';

let id = 0;

/**
 * A component that accepts a Broadcaster and a child that's a React component.
 * When the broadcaster calls setState, its state will be passed as props to the child component.
 * A ReceivingElement is "invisible" in that it adds no extra layer to the DOM; it renders the child as-is.
 * A ReceivingElement is a PureComponent because it need not know when its own props are updated;
 * the broadcaster internally _tells_ it when to update.
 */
export default class ReceivingElement extends PureComponent {

    static propTypes = {
        broadcaster: PropTypes.instanceOf(Broadcaster).isRequired,
        children: PropTypes.element.isRequired,
        waitForBroadcaster: PropTypes.bool,
    };

    static defaultProps = {
        waitForBroadcaster: false,
    };

    componentDidUpdate(prevProps) {
        if (this.props.broadcaster.constructor.getId() !== prevProps.broadcaster.constructor.getId()) {
            prevProps.broadcaster.removeUpdater(this.id);
        }
    }

    componentWillUnmount() {
        this.props.broadcaster.removeUpdater(this.id);
    }

    constructor(props) {
        super(props);
        this.update = this.update.bind(this);
        this.state = { updateCount: 0 };
        this.id = id++;
    }

    render() {
        const { children, broadcaster } = this.props;
        if (!broadcaster.updater) {
            broadcaster.setUpdater(this.update, this.id);
        }
        const broadcasterState = broadcaster.getState();
        const isBroadcasterStateEmpty = isEmpty(broadcasterState);
        if (this.props.waitForBroadcaster && isBroadcasterStateEmpty) {
            return null;
        }

        const element = isBroadcasterStateEmpty ? children : cloneElement(children, broadcasterState);
        return (
            <>
                {element}
            </>
        )
    }

    update() {
        this.forceUpdate();
    }
}