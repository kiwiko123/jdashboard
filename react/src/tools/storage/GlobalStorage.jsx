
class GlobalStorage {
    constructor() {
        super();
        this.__storage = new Map();
    }

    has(key) {
        return this.__storage.has(key);
    }

    get(key) {
        return this.__storage.get(key);
    }

    remove(key) {
        return this.__storage.delete(key);
    }
}

export default new GlobalStorage();