import React, { PureComponent, cloneElement, useState } from 'react';
import PropTypes from 'prop-types';
import { isEmpty } from 'lodash';
import Receiver from '../Receiver';
import { logger } from '../../common/js/logs';

/**
 * A component that accepts a Receiver and a child that's a React component.
 * When the receiver calls setState, its state will be passed as props to the child component.
 * A ReceivingElement is "invisible" in that it adds no extra layer to the DOM; it renders the child as-is.
 * A ReceivingElement is a PureComponent because it need not know when its own props are updated;
 * the receiver internally _tells_ it when to update.
 */
export default class ReceivingElement extends PureComponent {

    static propTypes = {
        receiver: PropTypes.instanceOf(Receiver).isRequired,
        children: PropTypes.element.isRequired,
        delayRenderForReceiver: PropTypes.bool,
    };

    static defaultProps = {
        delayRenderForReceiver: false,
    };

    constructor(props) {
        super(props);
        this.update = this.update.bind(this);
        this.state = { updateCount: 0 };
    }

    render() {
        const { children, receiver } = this.props;
        let childrenProps = {};

        if (!receiver.updater) {
            receiver.setUpdater(this.update)
        }

        childrenProps = receiver.getState();
        if (this.props.delayRenderForReceiver && isEmpty(childrenProps)) {
            logger.debug(`No state from ${this.props.receiver.getId()}; skipping render`)
            return null;
        }

        return (
            <>
                {cloneElement(children, childrenProps)}
            </>
        )
    }

    update() {
        const { updateCount } = this.state;
        this.setState({ updateCount: updateCount + 1 });
        logger.debug(`ReceivingElement re-render #${updateCount}`);
    }
}