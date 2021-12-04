import { get } from 'lodash';
import StateTransmitter from 'state/StateTransmitter';
import Request from 'common/js/Request';

export default class ChatroomModalStateManager extends StateTransmitter {

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