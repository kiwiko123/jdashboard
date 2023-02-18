
class GlobalStorage {
    constructor() {
        this.__storage = new Map();
    }

    has(key) {
        return this.__storage.has(key);
    }

    get(key) {
        return this.__storage.get(key);
    }

    set(key, data) {
        this.__storage.set(key, data);
        return true;
    }

    remove(key) {
        return this.__storage.delete(key);
    }
}

export default new GlobalStorage();