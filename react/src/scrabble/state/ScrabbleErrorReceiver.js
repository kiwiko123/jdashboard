import { get } from 'lodash';
import Receiver from '../../state/Receiver';
import ScrabbleGameBroadcaster from './ScrabbleGameBroadcaster';
import { BANNER_TYPES } from '../../common/components/Banner';

export default class ScrabbleErrorReceiver extends Receiver {
    funnel(state, sourceId) {
        if (sourceId === ScrabbleGameBroadcaster.getId()) {
            const errors = get(state, 'errors', []);
            const alerts = errors.map((errorMessage, index) => ({
                id: index,
                type: BANNER_TYPES.danger,
                message: errorMessage,
            }));
            this.setState({ alerts });
        }
    }
}