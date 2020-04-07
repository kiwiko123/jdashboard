import { get } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import ScrabbleGameBroadcaster from './ScrabbleGameBroadcaster';
import { BANNER_TYPES } from '../../common/components/Banner';

export default class ScrabbleErrorReceiver extends Broadcaster {
    receive(state, broadcasterId) {
        if (broadcasterId === ScrabbleGameBroadcaster.getId()) {
            const errors = get(state, 'errors', []);
            const alerts = errors.map((errorMessage, index) => ({
                id: `${broadcasterId}-${index}`,
                type: BANNER_TYPES.danger,
                message: errorMessage,
            }));
            this.setState({ alerts });
        }
    }
}