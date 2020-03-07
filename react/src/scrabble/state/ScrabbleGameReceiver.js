import Receiver from '../../state/Receiver';

export default class ScrabbleGameReceiver extends Receiver {
    funnel(state, sourceId) {
        this.setState(state);
    }
}