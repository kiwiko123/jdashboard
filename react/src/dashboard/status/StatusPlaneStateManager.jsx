import StateManager from 'state/StateManager';

export function GLOBAL_addMessage(messagePayload) {}

export default class StatusPlaneStateManager extends StateManager {
    constructor() {
        super();
        this.addState({
            messages: [],
        });

        if (!GLOBAL_addMessage.isWired) {
            GLOBAL_addMessage = (messagePayload) => {
                GLOBAL_addMessage.isWired = true;
                return this.addMessage(messagePayload);
            };
        }
    }

    addMessage(messagePayload) {
        const messages = [
            messagePayload,
            ...this.state.messages,
        ];
        this.setState({ messages });

        setTimeout(
            () => {
                const messages = this.state.messages.filter(message => message.id !== messagePayload.id);
                this.setState({ messages });
            },
            5000,
        );
    }
}