# Component State Management
_These implementations are inspired by [refluxjs](https://github.com/reflux/refluxjs)._

The files defined here provide a class-based way to abstract heavy logic out of a React component.
Broadcasters handle the core logic, including web requests and state management.
Receivers intake broadcasters' state, transform it into a format that a React component can directly ingest, 
and pass it into a component as props.

Both broadcasters and receivers have parallel state management concepts to React components;
`setState` calls trigger component re-renders.
## How does all this work?
### Broadcaster
A broadcaster has two core methods -- `register` and `setState`.
Its purpose is to handle all core state management logic including web requests, business logic, and in-memory state. 
#### `register`
A broadcaster can register as many receivers as it wants.
#### `setState`
Similar to a React class-based component, this method takes in an object and merges it with `this.state`.
Invoking `setState` will pass the entirety of `this.state` into all registered receivers.
### Receiver
A receiver also has two core methods -- `funnel` and `setState`.
Its purpose is to transform a broadcaster's state into a format that a React component can directly take in and render.
#### `funnel`
When a broadcaster calls its `setState` method, its entire state is "funnelled" into all registered receivers.
A receiver can identify a broadcaster through the `sourceId` parameter, which can be compared with a broadcaster's static `getId()` method.
#### `setState`
Calls to this method will signal a `ReceivingElement` to re-render, passing all of this receiver's state into the child component.

## Okay, show me an example!
```javascript
// MyBroadcaster.js
import { createAction } from './actions';

export default class MyBroadcaster extends Broadcaster {
    constructor() {
        super();
        this.state = {
            count: 0,
            incrementCount: createAction(this.updateCount.bind(this)),
        };
    }

    updateCount() {
        const { count } = this.state;
        this.setState({ count: count + 1 });
    }
}
```
```javascript
// MyReceiver.js
import MyBroadcaster from './MyBroadcaster';

export default class MyReceiver extends Receiver {
    funnel(state, sourceId) {
        switch (sourceId) {
            case MyBroadcaster.getId():
                this.setState(state);
                break;
            default:
                console.debug(`Unexpected broadcaster "${sourceId}"`);
        }
    }
}
```
```javascript
// MyPage.jsx
import MyBroadcaster from './MyBroadcaster';
import MyReceiver from './MyReceiver';
import ReceivingElement from './components/ReceivingElement';
import MyComponent from './components/MyComponent';

const MyPage = () => {
    const myBroadcaster = new MyBroadcaster();
    const myReceiver = new MyReceiver([myBroadcaster]);

    return (
        <div className="MyPage">
            <ReceivingElement receiver={myReceiver}>
                <MyComponent />
            </ReceivingElement>
        </div>
    );
};
```
```javascript
// MyComponent.jsx
const MyComponent = ({ count, incrementCount }) => (
    <div className="MyComponent">
        <button onClick={incrementCount}>
            Increment count
        </button>
        {`The count is ${count}`}
    </div>
);

MyComponent.propTypes = {
    count: PropTypes.number,
    incrementCount: PropTypes.func,
};

MyComponent.defaultProps = {
    count: 0;
    incrementCount: () => {},
};

export default MyComponent;
```