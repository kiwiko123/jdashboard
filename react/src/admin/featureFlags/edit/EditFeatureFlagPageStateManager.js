import { get, isEmpty, set } from 'lodash';
import FormStateManager from 'tools/forms/state/FormStateManager';
import logger from 'common/js/logging';
import Request from 'tools/http/Request';

export default class EditFeatureFlagPageStateManager extends FormStateManager {
    constructor({ featureFlagId }) {
        super();

        this.featureFlagId = featureFlagId;
    }
}