import StateManager from 'state/StateManager';

export default class ChatroomToolbarStateManager extends StateManager {

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