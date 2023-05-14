import { endsWith, isEmpty, isNumber, isObject, startsWith } from 'lodash';

function normalizeUrl(base, url) {
    if (!isEmpty(base)) {
        if (!startsWith(url, '/')) {
            url = `/${url}`;
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

function transformResponse(responseText) {
    return isEmpty(responseText) ? null : JSON.parse(responseText);
}

function checkResponseStatus(response) {
    if (!isObject(response)) {
        return;
    }
    if (isNumber(response.status) && response.status !== 200) {
        throw new Error(response.message);
    }
}

export {
    normalizeUrl,
    makeUrl,
    checkResponseStatus,
    transformResponse,
};