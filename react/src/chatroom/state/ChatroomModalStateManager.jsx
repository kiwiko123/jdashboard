import StateManager from 'state/StateManager';

export default class ChatroomModalStateManager extends StateManager {

    constructor() {
        super();

        this.setState({
            isOpen: false,
            close: this.closeModal.bind(this),
            title: null,
        });
    }

    receiveChatroomToolbarStateManager(state, metadata) {
        if (metadata === 'openCreateRoomModal') {
            this.setState({
                isOpen: true,
                title: state.title,
                modalId: 'openCreateRoomModal',
            });
        }
    }

    closeModal() {
        this.setState({
            isOpen: false,
            title: null,
        })
    }
}