import { isEmpty, omit, pickBy } from 'lodash';
import { getServerUrl } from 'common/js/config';
import { checkResponseStatus, makeUrl, normalizeUrl, transformResponse } from './requestOperations';

export default class Request {
    static to(url) {
        const normalizedUrl = normalizeUrl(getServerUrl(), url);
        return new Request(normalizedUrl);
    }

    constructor(url) {
        this._url = url;
        this._requestParameters = {};
        this._body = {};
        this._transformResponse = transformResponse;
        this._handleErrors = checkResponseStatus;
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

    responseTransformer(responseTransformer) {
        this._transformResponse = responseTransformer;
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

    async patch(fetchParameters = {}) {
        return this.__makeCreateRequest('PATCH', fetchParameters);
    }

    async delete(fetchParameters = {}) {
        const url = makeUrl(this._url, this._requestParameters);
        return this.__makeRequest(url, {
            ...fetchParameters,
            method: 'DELETE',
        });
    }

    async __makeRequest(url, parameters) {
        const allParameters = {
            ...this._internalFetchParameters,
            ...parameters,
        };
        return fetch(url, allParameters)
            .then(response => response.text())
            .then(this._transformResponse)
            .then((response) => {
                this._handleErrors(response);
                return response;
            });
    }

    async __makeCreateRequest(method, fetchParameters = {}) {
        const url = makeUrl(this._url, this._requestParameters);
        const bodyParameters = isEmpty(this._body) ? {} : { body: JSON.stringify(this._body) };
        const parameters = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            ...bodyParameters,
            ...fetchParameters,
            method,
        };

        return this.__makeRequest(url, parameters);
    }
}