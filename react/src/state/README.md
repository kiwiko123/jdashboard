# Component State Management
_These implementations are inspired by [refluxjs](https://github.com/reflux/refluxjs) stores. They are not intended for production-level usage._

The files defined here provide a class-based way to abstract heavy logic out of a React component.
Broadcasters "broadcast" state to React components, or other broadcasters.
Broadcasters have parallel state management concepts to React components; `setState` calls trigger component re-renders.
## How does all this work?
### Broadcaster
A broadcaster has two core methods -- `register` and `setState`.
Its purpose is to handle all core state management logic including web requests, business logic, and in-memory state. 
#### `register`
A broadcaster can register as many other broadcasters as it wants. Registered broadcasters are also known as "listeners."
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
        this.state = {
            count: 0,
            incrementCount: this.updateCount.bind(this),
        };
    }

    updateCount() {
        const { count } = this.state;
        this.setState({ count: count + 1 });
    }
}
```
**MyPage.jsx**
```javascript
import MyBroadcaster from './MyBroadcaster';
import ReceivingElement from './components/ReceivingElement';
import MyComponent from './components/MyComponent';

const MyPage = () => {
    const myBroadcaster = new MyBroadcaster();

    return (
        <div className="MyPage">
            <ReceivingElement broadcaster={myBroadcaster}>
                <MyComponent />
            </ReceivingElement>
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