import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';

export default class ChatroomMessageInputStateManager extends StateManager {

    constructor(roomId) {
        super();

        this.roomId = roomId;
        this.setState({
            input: null,
        });
        this.registerMethod(this.setInput);
        this.registerMethod(this.sendMessage);
    }

    setInput(input) {
        this.setState({ input });
    }

    sendMessage() {
        const payload = {
            chatroomMessageRoomId: this.roomId,
            message: this.state.input,
        };

        Request.to('/chatroom/api/room/message')
            .authenticated()
            .body(payload)
            .post()
            .then((response) => {
                this.setState({ input: null });
                this.sendState('ChatroomMessageFeedStateManager', { chatroomMessageId: response.sentMessage.id }, 'messageSent');
            });
    }
}