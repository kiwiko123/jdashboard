import React, { PureComponent, cloneElement } from 'react';
import PropTypes from 'prop-types';
import Broadcaster from '../Broadcaster';
import { logger } from '../../common/js/logs';

/**
 * A component that accepts a Broadcaster and a child that's a React component.
 * When the broadcaster calls setState, its state will be passed as props to the child component.
 * A ReceivingElement is "invisible" in that it adds no extra layer to the DOM; it renders the child as-is.
 * A ReceivingElement is a PureComponent because it need not know when its own props are updated;
 * the broadcaster internally _tells_ it when to update.
 */
export default class ReceivingElement extends PureComponent {

    static id = 0;

    static propTypes = {
        broadcaster: PropTypes.instanceOf(Broadcaster).isRequired,
        children: PropTypes.element.isRequired,
    };

    componentWillUnmount() {
        this.props.broadcaster.removeUpdater(this.id);
    }

    constructor(props) {
        super(props);
        this.update = this.update.bind(this);
        this.state = { updateCount: 0 };
        this.id = this.constructor.id++;
    }

    render() {
        const { children, broadcaster } = this.props;
        if (!broadcaster.updater) {
            broadcaster.setUpdater(this.update, this.id);
        }
        const childrenProps = broadcaster.getState();

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