import { endsWith, identity, isEmpty, isNumber, omit, pickBy, startsWith } from 'lodash';
import { getServerUrl } from 'common/js/config';

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
    if (isEmpty(requestParameters)) {
        return url;
    }

    const query = Object.entries(requestParameters)
        .map(([key, value]) => [key, encodeURIComponent(value)])
        .map(pair => pair.join('='))
        .join('&');
    return `${url}?${query}`;
}

function makeUrl(url, requestParameters) {
    if (isEmpty(requestParameters)) {
        return url;
    }
    return buildRequestParameterUrl(url, requestParameters);
}

export default class Request {

    static to(url) {
        return new Request(url);
    }

    constructor(url) {
        this._url = normalizeUrl(getServerUrl(), url);
        this._requestParameters = {};
        this._body = {};
        this._extractResponse = identity;
        this._handleErrors = () => {};
        this._internalFetchParameters = {};
    }

    body(body) {
        this._body = pickBy(body, value => value !== undefined);
        return this;
    }

    requestParameters(requestParameters) {
        this._requestParameters = pickBy(requestParameters, value => value !== undefined);
        return this;
    }

    responseExtractor(responseExtractor) {
        this._extractResponse = responseExtractor;
        return this;
    }

    errorHandler(errorHandler) {
        this._handleErrors = errorHandler;
        return this;
    }

    authenticated(value = true) {
        if (value) {
            this._internalFetchParameters.credentials = 'include';
        } else {
            this._internalFetchParameters = omit('credentials');
        }
        return this;
    }

    async get(fetchParameters = {}) {
        const url = makeUrl(this._url, this._requestParameters);
        return this.__makeRequest(url, fetchParameters);
    }

    async post(fetchParameters = {}) {
        return this.__makeCreateRequest('POST', fetchParameters);
    }

    async put(fetchParameters = {}) {
        return this.__makeCreateRequest('PUT', fetchParameters);
    }

    async delete(fetchParameters = {}) {
        return this.__makeCreateRequest('DELETE', fetchParameters);
    }

    async patch(fetchParameters = {}) {
        return this.__makeCreateRequest('PATCH', fetchParameters);
    }

    async __makeRequest(url, parameters) {
        const allParameters = {
            ...this._internalFetchParameters,
            ...parameters,
        };
        return fetch(url, allParameters)
            .then(response => response.json())
            .then((response) => {
                this._handleErrors(response);
                if (isNumber(response.status) && response.status !== 200) {
                    throw new Error(response.message);
                }
                return this._extractResponse(response);
            });
    }

    async __makeCreateRequest(method, fetchParameters = {}) {
        const url = makeUrl(this._url, this._requestParameters);
        const parameters = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            ...fetchParameters,
            method,
        };
        if (!isEmpty(this._body)) {
            parameters.body = JSON.stringify(this._body);
        }

        return this.__makeRequest(url, parameters);
    }
}