import {
	endsWith,
	get as lget,
	identity,
	isEmpty,
	isNil,
	startsWith,
} from 'lodash';

const SERVER_URL = 'http://localhost:8080';

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

async function postBody(url, payload) {
    const params = {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    };

    return fetch(url, params)
        .then(response => response.json());
}

function extractResponse(response) {
    return lget(response, 'payload', {});
}

function handleErrors(response, errorHandler) {
    const errors = lget(response, 'errors', []);
    errorHandler(errors);
    return response;
}

class RequestService {

	constructor(base_url = '', persistentPayload = {}) {
		this._base_url = base_url
		this._persistentPayload = persistentPayload;
		this._responseExtractor = identity;
		this._errorHandler = identity;
	}

	create() {
	    return new RequestService(this._base_url, this._persistentPayload);
	}

	withResponseExtractor(responseExtractor) {
	    this._responseExtractor = responseExtractor;
	    return this;
	}

	withErrorHandler(errorHandler) {
	    this._errorHandler = errorHandler;
	    return this;
	}

	async get(url, payload = {}) {
		let requestUrl = this._normalize_url(url);
		if (!isEmpty(payload)) {
		    const data = {};
		    Object.entries(this._get_persistent_payload(payload))
		        .map(([key, value]) => [key, encodeURIComponent(value)])
		        .forEach(([key, value]) => { data[key] = value; });
		    const encodedParameters = encodeURIComponent(data);
            requestUrl = `${requestUrl}?${encodedParameters}`;
		}

		return fetch(requestUrl)
		    .then(response => response.json())
		    .then(this._errorHandler)
		    .then(this._responseExtractor);
	}

	async post(url, payload) {
		url = this._normalize_url(url);
		const requestData = this._get_persistent_payload(payload);
		return postBody(url, requestData)
		    .then(this._errorHandler)
            .then(this._responseExtractor);
	}

	setPersistentPayload(payload) {
	    this._persistentPayload = payload;
	}

	_normalize_url(url) {
		return normalizeUrl(this._base_url, url);
	}

	_get_persistent_payload(payload = {}) {
		const result = {
			...this._persistentPayload,
			...payload,
		};

		return result;
	}
}

// new API
async function get(url, { requestParameters = {}, includeCredentials = false } = {}) {
    let requestUrl = normalizeUrl(SERVER_URL, url);
    if (!isEmpty(requestParameters)) {
        const data = {};
        Object.entries(requestParameters)
            .map(([key, value]) => [key, encodeURIComponent(value)])
            .forEach(([key, value]) => { data[key] = value; });
        const encodedParameters = encodeURIComponent(data);
        requestUrl = `${requestUrl}?${encodedParameters}`;
    }

    let fetchPromise;
    if (includeCredentials) {
        fetchPromise = fetch(requestUrl, { includeCredentials });
    } else {
        fetchPromise = fetch(requestUrl);
    }

    return fetchPromise
        .then(response => response.json())
        .then(payload => isNil(payload) ? {} : payload);
}

export {
    get,
    extractResponse,
    handleErrors,
    RequestService,
};