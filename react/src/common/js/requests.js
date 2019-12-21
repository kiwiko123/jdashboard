import {
	endsWith,
	get,
	isEmpty,
	startsWith
} from 'lodash';

async function postBody(url, payload) {
    const params = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    };

    const response = await fetch(url, params);
    return await response.json();
}

export default class RequestService {

	constructor(base_url = '', persistentPayload = {}) {
		this._base_url = base_url
		this._persistentPayload = persistentPayload;
	}

	async get(url) {
		url = this._normalize_url(url);
		const response = await fetch(url);
		return await response.json();
	}

	async post(url, payload) {
		url = this._normalize_url(url);
		const requestData = this._get_persistent_payload(payload);
		return postBody(url, requestData);
	}

	_normalize_url(url) {
		if (!isEmpty(this._base_url)) {
			if (!startsWith(url, '/')) {
				url = `/${url}`;
			}
			if (!endsWith(url, '/')) {
				url = `${url}/`;
			}
		}

		return `${this._base_url}${url}`;
	}

	_get_persistent_payload(payload = {}) {
		const result = {
			...this._persistentPayload,
			...payload,
		};

		return result;
	}
}