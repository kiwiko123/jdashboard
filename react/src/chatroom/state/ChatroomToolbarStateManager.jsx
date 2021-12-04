import { get } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import Request from 'common/js/Request';

export default class ChatroomToolbarStateManager extends StateTransmitter {

    constructor() {
        super();

        this.setState({
            actions: {
                openCreateRoomModal: this.openCreateRoomModal.bind(this),
            },
        });
    }

    openCreateRoomModal() {
        const payload = {
            title: 'Create Chatroom',
        };
        this.sendState('ChatroomModalStateManager', payload, 'openCreateRoomModal');
    }
}