import { isEmpty } from 'lodash';

function objectReducer(accumulator, [key, value]) {
    Object.assign(accumulator, { [key]: value });
    return accumulator;
}

export function getUrlParameters() {
    // skip the '?'
    const parameters = window.location.search.substr(1);
    if (isEmpty(parameters)) {
        return {};
    }

    return parameters.split('&')
        .map(pair => pair.split('='))
        .map(pair => pair.map(decodeURIComponent))
        .reduce(objectReducer, {});
}

export function updateQueryParameters(parameters = {}) {
    if (isEmpty(parameters)) {
        return;
    }

    const updatedParameters = {
        ...getUrlParameters(),
        ...parameters,
    };

    const encodedParameters = Object.entries(updatedParameters)
        .map(pair => pair.map(encodeURIComponent))
        .map(([key, value]) => `${key}=${value}`)
        .join('&');

    let url = window.location.pathname;
    if (!isEmpty(encodedParameters)) {
        url = `${url}?${encodedParameters}`;
    }

    window.history.replaceState(null, '', url);
}