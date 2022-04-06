# Component State Management
_These implementations are inspired by [refluxjs](https://github.com/reflux/refluxjs) stores. They are not intended for production-level usage._

A state manager is a class-based singleton that abstracts heavy logic out of a React component,
by transmitting its state to either React components or other managers.
State managers have parallel state management concepts to React components; `setState` calls trigger component re-renders.
## How does all this work?
### State manager
A state manager has a few core methods -- `setState`, `sendState`, and `receiveState`.
Its purpose is to handle all core state management logic including web requests, business logic, and in-memory state.
#### `setState`
Similar to a React class-based component, this method takes in an object and one-dimensionally merges it with `this.state`.
By default, invoking `setState` triggers a component re-render.
#### `sendState`
Call this to send state to another manager. 
For example, you might create different managers to handle different areas of logic. It can be useful to share state between them.
#### `receiveState`
This function is invoked when another manager has sent its state to this one. The sender's ID tag, 
the data it's sending over, and any optional metadata are received.

## Okay, show me an example!
Consider an example where we're tracking the number of times a user clicks a button. 
We'll create a state manager whose job is to manage the count.

We also want to display a page-level alert once the user has clicked the button 10 times. 
Since this is a separate piece of logic, we can create a different state manager to handle that. 
The count incrementor manager can communicate with the page alert manager to let it know when to display the alert.

### CountIncrementingStateManager.js
```javascript
import StateManager from 'state/StateManager';

export default class CountIncrementingStateManager extends StateManager {
    constructor() {
        super();
        this.setState({ count: 0 });
        this.registerMethod(this.incrementCount); // Shorthand for `this.setState({ incrementCount: this.incrementCount.bind(this) })`
    }

    incrementCount() {
        const newCount = this.state.count + 1;
        this.setState({ count: newCount });
        
        if (newCount >= 10) {
            this.sendState('PageAlertStateManager', { count: newCount }, 'highCountNumber');
        }
    }
}
```
### PageAlertStateManager.js
```javascript
import StateManager from 'state/StateManager';

export default class PageAlertStateManager extends StateManager {
    constructor() {
        super();
        this.setState({ message: null });
    }

    // For convenience, a state manager can define a function named "receiveAAA", where "AAA" is a name of another state manager.
    // When that state manager sends its state to this one, it will be routed to this function.
    // In the case that a specific "receiveAAA" function is not defined, the receiving state will be routed to the `receive` function.
    receiveCountIncrementingStateManager(state, metadata) {
        if (metadata === 'highCountNumber') {
            this.setState({ message: `Wow, you've clicked the button ${state.count} times!` });
        }
    }
    
    receive(tag, state, metadata) {

    }
}
```
### CountIncrementor.jsx
A manager's state can be directly passed into a component as props (shown further below).  
In this case, `count` and `incrementCount` come from `CountIncrementingStateManager`.
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
Likewise here -- `message` comes from `PageAlertStateManager`'s state.
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
Components and state managers are linked together via a `ComponentStateManager`. 
It passes the manager's state to the component as props. 
By default, the manager's `setState` calls trigger component re-renders. 
```javascript
import ComponentStateManager from 'state/components/ComponentStateManager';
import { useStateManager } from 'state/hooks';
import PageAlertStateManager from './PageAlertStateManager';
import CountIncrementingStateManager from './CountIncrementingStateManager';
import PageAlerts from './PageAlerts';
import CountIncrementor from './CountIncrementor';

const MyPage = () => {
    const countIncrementingStateManager = useStateManager(() => new CountIncrementingStateManager());
    const pageAlertStateManager = useStateManager(() => new PageAlertStateManager());

    return (
        <div className="MyPage">
            <ComponentStateManager
                stateManager={pageAlertStateManager}
                component={PageAlerts}
                canResolve={state => state.message}
            />
            <ComponentStateManager
                stateManager={countIncrementingStateManager}
                component={CountIncrementor}
            />
        </div>
    );
};
```
