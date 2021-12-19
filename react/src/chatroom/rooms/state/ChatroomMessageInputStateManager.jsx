import PushServiceStateTransmitter from 'tools/pushService/state/PushServiceStateTransmitter';
import Request from 'tools/http/Request';

export default class ChatroomMessageInputStateManager extends PushServiceStateTransmitter {

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
            });
    }
}