import {
	endsWith,
	isEmpty,
	startsWith,
} from 'lodash';

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
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    };

    return fetch(url, params)
        .then(response => response.json());
}

export class RequestService {

	constructor(base_url = '', persistentPayload = {}) {
		this._base_url = base_url
		this._persistentPayload = persistentPayload;
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
		    .then(response => response.json());
	}

	async post(url, payload) {
		url = this._normalize_url(url);
		const requestData = this._get_persistent_payload(payload);
		return postBody(url, requestData);
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
