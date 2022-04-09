import ClientSessionManager from 'tools/clientSessions/ClientSessionManager';
import PushServiceSessionManager from 'tools/pushService/private/PushServiceSessionManager';

class LifecycleManager {

    constructor() {
        this.clientSessionManager = new ClientSessionManager();
    }

    setUp() {
//         this.clientSessionManager.createNewSession();
    }

    tearDown() {
//         this.clientSessionManager.endSession();
        PushServiceSessionManager.purge();
    }
}

const LIFECYCLE_MANAGER = new LifecycleManager();

function setUp() {
    LIFECYCLE_MANAGER.setUp();
}

function tearDown() {
    LIFECYCLE_MANAGER.tearDown();
}

export default {
    setUp,
    tearDown,
}