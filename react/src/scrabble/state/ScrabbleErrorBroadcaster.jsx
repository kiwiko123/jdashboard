import { get } from 'lodash';
import Broadcaster from '../../state/Broadcaster';
import ScrabbleGameBroadcaster from './ScrabbleGameBroadcaster';
import { BANNER_TYPES } from '../../common/components/Banner';
import DashboardAlertActions from '../../dashboard/state/actions/DashboardAlertActions';

export default class ScrabbleErrorReceiver extends Broadcaster {

    receive(state, broadcasterId) {
        if (broadcasterId === ScrabbleGameBroadcaster.getId()) {
            // General error messages
            const errorMessages = [...get(state, 'errors', [])];

            // Invalid tiles
            const invalidSubmittedTiles = get(state, 'player.invalidSubmittedTiles', []);
            if (invalidSubmittedTiles.length > 0) {
                const invalidTilesMessage = get(state, 'invalidSubmittedTiles', [])
                    .map(tile => tile.character)
                    .join(', ');
                const pluralModifier = invalidSubmittedTiles.length === 1 ? '' : 's';
                errorMessages.push(`Invalid tile${pluralModifier} "${invalidTilesMessage}"`);
            }

            errorMessages.map(errorMessage => ({
                bannerType: BANNER_TYPES.danger,
                message: errorMessage,
                dismissable: true,
            }))
            .forEach(DashboardAlertActions.addAlert);
        }
    }
}