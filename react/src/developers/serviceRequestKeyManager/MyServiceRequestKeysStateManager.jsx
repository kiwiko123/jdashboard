import { get } from 'lodash';
import StateManager from 'state/StateManager';
import Request from 'tools/http/Request';
import logger from 'tools/monitoring/logging';

export default class MyServiceRequestKeysStateManager extends StateManager {
    constructor() {
        super();

        this.setState({
            serviceRequestKeys: [],
        });

        this.fetchMyServiceRequestKeys();
    }

    fetchMyServiceRequestKeys() {
        Request.to('/developers/service-request-keys/app-api/mine')
            .authenticated()
            .get()
            .then((response) => {
                const serviceRequestKeys = get(response, 'serviceRequestKeys', []);
                this.setState({ serviceRequestKeys });
            });
    }
}