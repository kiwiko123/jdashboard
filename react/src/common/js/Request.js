import { endsWith, get, isEmpty, isNil, startsWith } from 'lodash';
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

function extractResponse(response) {
    const payload = get(response, 'payload', {});
    return isNil(payload) ? {} : payload;
}

export default class Request {

    constructor(url) {
        this.url = normalizeUrl(getServerUrl(), url);
        this.requestParameters = {};
        this.body = {};
        this.extractResponse = extractResponse;
        this.handleErrors = () => {};
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

    async get(fetchParameters = {}) {
        let requestUrl = this.url;
        if (!isEmpty(this.requestParameters)) {
            requestUrl = buildRequestParameterUrl(requestUrl, this.requestParameters);
        }

        const parameters = { ...fetchParameters };
        return fetch(requestUrl, parameters)
            .then(response => response.json())
            .then((response) => {
                this.handleErrors(response);
                return this.extractResponse(response);
            });
    }

    async post(fetchParameters = {}) {
        let requestUrl = this.url;
        if (!isEmpty(this.requestParameters)) {
            requestUrl = buildRequestParameterUrl(requestUrl, this.requestParameters);
        }

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

        return fetch(requestUrl, parameters)
            .then(response => response.json())
            .then((response) => {
                this.handleErrors(response);
                return this.extractResponse(response);
            });
    }
}