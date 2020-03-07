
// Source: https://hackernoon.com/creating-callable-objects-in-javascript-d21l3te1
class Callable extends Function {
    constructor() {
        super();
        return new Proxy(this, {
            apply: (target, self, args) => target._call(...args),
        });
    }

    _call(...args) { }
}

class SubscriptionAction extends Callable {
    constructor(properties = {}) {
        const proxy = super();
        this.properties = properties;
        this.handler = () => {};
        return proxy;
    }

    withSubscription(handler) {
        this.handler = handler;
        return this;
    }

    _call(...args) {
        return this.handler(...args);
    }
}


let subscriptionId = 0;
const subscriptionIds = new Set([]);

function getUniqueSubscriptionId(name) {
    let iterations = 0;
    let id = `${name}Event-${subscriptionId}`;

    do {
        id = `${id}${subscriptionId}`;
    } while (subscriptionIds.has(id) && iterations++ < 10);

    ++subscriptionId;
    subscriptionIds.add(id);

    return id;
}

const ACTION_TYPE_ID = 'SUBSCRIBABLE-ACTION-FUNCTION';

export function createAction(subscriber) {
    const properties = {
       id: getUniqueSubscriptionId(''),
       type: ACTION_TYPE_ID,
   };
    return new SubscriptionAction(properties)
        .withSubscription(subscriber);
}