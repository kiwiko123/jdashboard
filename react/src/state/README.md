# Component State Management
_These implementations are inspired by [refluxjs](https://github.com/reflux/refluxjs) stores. They are not intended for production-level usage._

A Broadcaster is a class-based singleton that abstracts heavy logic out of a React component,
by "broadcasting" its state to React components or other broadcasters.
Broadcasters have parallel state management concepts to React components; `setState` calls trigger component re-renders.
## How does all this work?
### Broadcaster
A broadcaster has two core methods -- `listenTo` and `setState`.
Its purpose is to handle all core state management logic including web requests, business logic, and in-memory state. 
#### `listenTo`
A broadcaster can listen to as many other broadcasters as it wants. "Listeners" will receive the broadcaster's state updates.
#### `setState`
Similar to a React class-based component, this method takes in an object and one-dimensionally merges it with `this.state`.
Invoking `setState` will pass the entirety of `this.state` into all listeners.

## Okay, show me an example!
**MyBroadcaster.js**
```javascript
import Broadcaster from './Broadcaster';

export default class MyBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.setState({ count: 0 });
        this.registerMethod(this.incrementCount); // the same as `this.setState({ incrementCount: this.incrementCount.bind(this) })`
    }

    incrementCount() {
        const { count } = this.state;
        this.setState({ count: count + 1 });
    }
}
```
**MyPage.jsx**
```javascript
import MyBroadcaster from './MyBroadcaster';
import ComponentStateManager from './components/ComponentStateManager';
import MyComponent from './components/MyComponent';

const MyPage = () => {
    const myBroadcaster = new MyBroadcaster();

    return (
        <div className="MyPage">
            <ComponentStateManager
                broadcaster={myBroadcaster}
                component={MyComponent}
            />
        </div>
    );
};
```
**MyComponent.jsx**
```javascript
const MyComponent = ({ count, incrementCount }) => (
    <div className="MyComponent">
        <button onClick={incrementCount}>
            Increment count
        </button>
        {`The count is ${count}`}
    </div>
);
```
## Conditional rendering
Consider another example, where we want to render a greeting message with the logged-in user's name.
In the broadcaster's constructor, an asynchronous web request is fired off to determine the logged-in user.
When the response comes back, the value is set on state.

But, we _only_ want to show this greeting message if someone's currently logged in.
Fortunately, `ComponentStateManager`'s `canResolve` prop makes this easy --
it's a function that takes in the broadcaster's state and returns true if the component should render, or false if not.
By default, this always returns true, but we can customize it to our needs.

**UserGreetingBroadcaster.jsx**
```javascript
class UserGreetingBroadcaster extends Broadcaster {
    constructor() {
        super();
        asynchronouslyFetchCurrentUser()
            .then((user) => {
                this.setState({ userName: user.userName });
            });
    }
```
**MyPage.jsx**
```javascript
const MyPage = () => {
    const userGreetingBroadcaster = new UserGreetingBroadcaster();
    return (
        <div className="MyPage">
            <ComponentStateManager
                broadcaster={userGreetingBroadcaster}
                component={UserGreetingText}
                canResolve={state => state.userName}
            />
        </div>
    );
};
```
**UserGreetingText.jsx**
```javascript
const UserGreetingText = ({ userName }) => {
    return (
        <div className="UserGreetingText">
            <span>{`Hello, ${userName}`}</span>
        </div>
    );
};

UserGreetingText.propTypes = {
    userName: PropTypes.string.isRequired,
};
```
## Broadcaster Interface
```javascript
class Broadcaster {
    /**
     * One-dimensionally merges the input state with this broadcaster's state.
     * Passes state into all registered ComponentStateManagers and listening broadcasters.
     *
     * Do not override this.
     */
    setState(newState) { }

    /**
     * When a broadcaster calls setState, listeners will receive its state through this method.
     * The invoking broadcaster can be identified through the broadcasterId argument.
     * Override this to control state ingestion.
     */
    receive(state, broadcasterId) { }

    /**
     * Subscribes this to the argument broadcaster;
     * that is, when the argument broadcaster makes state updates, this will receive it.
     *
     * If a cycle is detected within the listeners, this operation will silently fail.
     *
     * When listenTo is called, this will immediately receive the broadcaster's state.
     */
    listenTo(broadcaster) { }

    /**
     * Return this broadcaster's state.
     * Returning a copy of the state may be safer, but returning a reference may be more performant.
     */
    getState() { }
}
```