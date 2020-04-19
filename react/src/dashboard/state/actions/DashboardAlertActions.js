import { random } from 'lodash';
import { invokeAction } from '../../../state/actions';
import DashboardAlertBroadcaster from '../DashboardAlertBroadcaster';

function addAlert({ message, bannerType, dismissable = false, autoDismissMillis }) {
    const id = random(9999);
    const bannerData = { message, bannerType, id, dismissable, autoDismissMillis };
    invokeAction(DashboardAlertBroadcaster.getId(), 'addAlert', action => action(bannerData));
}

export default {
    addAlert,
};