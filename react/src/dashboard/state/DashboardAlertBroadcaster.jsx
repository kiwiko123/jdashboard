import { isNumber, takeRight } from 'lodash';
import Broadcaster from '../../state/Broadcaster';

export default class DashboardAlertBroadcaster extends Broadcaster {

    constructor() {
        super();
        this.setState({
            alerts: [],
            removeAlert: this.removeAlert.bind(this),
            maxAlerts: 5,
        });

        this.registerGlobalAction('addAlert', this.addAlert.bind(this));
        this.registerGlobalAction('removeAlert', this.removeAlert.bind(this));
    }

    addAlert({ message, bannerType, id, dismissable, autoDismissMillis }) {
        let currentAlerts = this.state.alerts;
        const { maxAlerts } = this.state;
        if (isNumber(maxAlerts) && currentAlerts.length >= maxAlerts) {
            currentAlerts = takeRight(currentAlerts, maxAlerts - 1);
        }

        const alerts = [...currentAlerts, { message, bannerType, dismissable, id }];
        this.setState({ alerts });

        if (isNumber(autoDismissMillis) && autoDismissMillis > 0) {
            setTimeout(() => this.removeAlert(id), autoDismissMillis);
        }
    }

    removeAlert(alertId) {
        const alerts = this.state.alerts.filter(alert => alert.id !== alertId);
        this.setState({ alerts });
    }
}