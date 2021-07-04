# Component State Management
_These implementations are inspired by [refluxjs](https://github.com/reflux/refluxjs) stores. They are not intended for production-level usage._

A state transmitter is a class-based singleton that abstracts heavy logic out of a React component,
by "transmitting" its state to either React components or other transmitters.
State transmitters have parallel state management concepts to React components; `setState` calls trigger component re-renders.
## How does all this work?
### State Transmitter
A state transmitter has a few core methods -- `setState`, `sendState`, and `receiveState`.
Its purpose is to handle all core state management logic including web requests, business logic, and in-memory state.
#### `setState`
Similar to a React class-based component, this method takes in an object and one-dimensionally merges it with `this.state`.
By default, invoking `setState` triggers a component re-render.
#### `sendState`
Call this to send or transmit state to another transmitter. 
For example, you might create different transmitters to handle different areas of logic. It can be useful to share state between transmitters.
#### `receiveState`
This function is invoked when another transmitter has sent its state to this transmitter. The sending transmitter's ID tag, 
the data it's sending over, and any optional metadata are received.

## Okay, show me an example!
Consider an example where we're tracking the number of times a user clicks a button. 
We'll create a state transmitter whose job is to manage the count.

We also want to display a page-level alert once the user has clicked the button 10 times. 
Since this is a separate piece of logic, we can create a different state transmitter to handle that. 
The count incrementor transmitter can communicate with the page alert transmitter to let it know when to display the alert.

### CountIncrementingStateTransmitter.js
```javascript
import StateTransmitter from 'state/StateTransmitter';

export default class CountIncrementingStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({ count: 0 });
        this.registerMethod(this.incrementCount); // the same as `this.setState({ incrementCount: this.incrementCount.bind(this) })`
    }

    incrementCount() {
        const newCount = this.state.count + 1;
        this.setState({ count: newCount });
        
        if (newCount >= 10) {
            this.sendState('PageAlertStateTransmitter', { count: newCount }, 'highCountNumber');
        }
    }
}
```
### PageAlertStateTransmitter.js
```javascript
import StateTransmitter from 'state/StateTransmitter';

export default class PageAlertStateTransmitter extends StateTransmitter {
    constructor() {
        super();
        this.setState({ message: null });
    }

    // For convenience, a state transmitter can define a function named "receiveAAA", where "AAA" is a name of another state transmitter.
    // When that state transmitter sends its state to this one, it will be routed to this function.
    // In the case that a specific "receiveAAA" function is not defined, the receiving state will be routed to the `receive` function.
    receiveCountIncrementingStateTransmitter(state, metadata) {
        if (metadata === 'highCountNumber') {
            this.setState({ message: `Wow, you've clicked the button ${state.count} times!` });
        }
    }
    
    receive(tag, state, metadata) {

    }
}
```
### CountIncrementor.jsx
When a `ComponentStateManager` links a component with a state transmitter (in `MyPage.jsx` below), 
the transmitter's state is passed into the component as props. 
In this case, `count` and `incrementCount` come from `CountIncrementingStateTransmitter`.
```javascript
const CountIncrementor = ({ count, incrementCount }) => (
    <div className="CountIncrementor">
        <button onClick={incrementCount}>
            Increment count
        </button>
        {`The count is ${count}`}
    </div>
);

CountIncrementor.propTypes = {
    count: PropTypes.number.isRequired,
    incrementCount: PropTypes.function.isRequired,
};
```
### PageAlerts.jsx
Likewise here -- `message` comes from `PageAlertStateTransmitter`'s state.
```javascript
const PageAlerts = ({ message }) => (
    <div className="PageAlerts">
        <span>{message}</span>
    </div>
);

PageAlerts.propTypes = {
    message: PropTypes.string,
};
```
### MyPage.jsx
Components and state transmitters are linked together via a `ComponentStateManager`. 
The manager passes the transmitter's state to the component as props. 
By default, the transmitter's `setState` calls trigger component re-renders. 
```javascript
import ComponentStateManager from 'state/components/ComponentStateManager';
import PageAlertStateTransmitter from './PageAlertStateTransmitter';
import CountIncrementingStateTransmitter from './CountIncrementingStateTransmitter';
import PageAlerts from './PageAlerts';
import CountIncrementor from './CountIncrementor';

const MyPage = () => {
    const countIncrementingStateTransmitter = new CountIncrementingStateTransmitter();
    const pageAlertStateTransmitter = new PageAlertStateTransmitter();

    return (
        <div className="MyPage">
            <ComponentStateManager
                broadcaster={pageAlertStateTransmitter}
                component={PageAlerts}
                canResolve={state => state.message}
            />
            <ComponentStateManager
                broadcaster={countIncrementingStateTransmitter}
                component={CountIncrementor}
            />
        </div>
    );
};
```
