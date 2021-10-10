import PushServiceStateTransmitter from 'tools/pushService/state/PushServiceStateTransmitter';

export default class DashboardNotificationsStateTransmitter extends PushServiceStateTransmitter {

    constructor(serviceId) {
        super(serviceId);
        this.setState({
            push: this.pushToServer.bind(this),
        });
    }

    onConnectionOpened() {
        this.pushToServer({
            message: 'Connection has been opened',
        });
    }
}