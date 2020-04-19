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
        .map(([key, value]) => [decodeURIComponent(key), decodeURIComponent(value)])
        .reduce(objectReducer, {});
}