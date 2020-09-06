import { endsWith, get, isEmpty, isNil, isNumber, omit, startsWith } from 'lodash';
import { getServerUrl } from './config';

function normalizeUrl(base, url) {
    if (!isEmpty(base)) {
        if (!startsWith(url, '/')) {
            url = `/${url}`;
        }
        if (!endsWith(url, '/')) {
            url = `${url}/`;
        }
    }
    return `${base}${url}`;
}

function buildRequestParameterUrl(url, requestParameters) {
    const data = {};
    Object.entries(requestParameters)
        .map(([key, value]) => [key, encodeURIComponent(value)])
        .forEach(([key, value]) => { data[key] = value; });
    const encodedParameters = encodeURIComponent(data);
    return `${url}?${encodedParameters}`;
}

function makeUrl(url, requestParameters) {
    if (isEmpty(requestParameters)) {
        return url;
    }
    return buildRequestParameterUrl(url, this.requestParameters);
}

function extractResponse(response) {
    const payload = get(response, 'payload', {});
    return isNil(payload) ? {} : payload;
}

export default class Request {

    static to(url) {
        return new Request(url);
    }

    constructor(url) {
        this.url = normalizeUrl(getServerUrl(), url);
        this.requestParameters = {};
        this.body = {};
        this.extractResponse = extractResponse;
        this.handleErrors = () => {};
        this._internalFetchParameters = {};
    }

    withBody(body) {
        this.body = body;
        return this;
    }

    withRequestParameters(requestParameters) {
        this.requestParameters = requestParameters;
        return this;
    }

    withResponseExtractor(responseExtractor) {
        this.extractResponse = responseExtractor;
        return this;
    }

    withErrorHandler(errorHandler) {
        this.handleErrors = errorHandler;
        return this;
    }

    withAuthentication(value = true) {
        if (value) {
            this._internalFetchParameters.credentials = 'include';
        } else {
            this._internalFetchParameters = omit('credentials');
        }
        return this;
    }

    async get(fetchParameters = {}) {
        const url = makeUrl(this.url, this.requestParameters);
        return this.__makeRequest(url, fetchParameters);
    }

    async post(fetchParameters = {}) {
        const url = makeUrl(this.url, this.requestParameters);
        const parameters = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            ...fetchParameters,
            method: 'POST',
        };
        if (!isEmpty(this.body)) {
            parameters.body = JSON.stringify(this.body);
        }

        return this.__makeRequest(url, parameters);
    }

    async put(fetchParameters = {}) {
        const url = makeUrl(this.url, this.requestParameters);
        const parameters = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            ...fetchParameters,
            method: 'PUT',
        };
        if (!isEmpty(this.body)) {
            parameters.body = JSON.stringify(this.body);
        }

        return this.__makeRequest(url, parameters);
    }

    async __makeRequest(url, parameters) {
        const allParameters = {
            ...this._internalFetchParameters,
            ...parameters,
        };
        return fetch(url, allParameters)
            .then(response => response.json())
            .then((response) => {
                this.handleErrors(response);
                if (isNumber(response.status) && response.status !== 200) {
                    throw new Error(response.message);
                }
                return this.extractResponse(response);
            });
    }
}