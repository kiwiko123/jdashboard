import { isEmpty } from 'lodash';

const FLAG_STATUS_DROPDOWN_OPTIONS = [
    {
        label: 'Enabled',
        value: 'enabled',
    },
    {
        label: 'Disabled',
        value: 'disabled',
    },
];

const USER_SCOPE_DROPDOWN_OPTIONS = [
    {
        label: 'Individual',
        value: 'individual',
    },
    {
        label: 'Public',
        value: 'public',
    },
];

export const FIELDS = {
    name: {
        name: 'flag-name',
        type: 'input',
        label: 'Flag name',
        value: null,
        isRequired: true,
        validate: value => !isEmpty(value),
    },
    status: {
        name: 'flag-status',
        type: 'dropdownSelector',
        label: 'Status',
        isRequired: true,
        validate: Boolean,
        options: FLAG_STATUS_DROPDOWN_OPTIONS,
    },
    value: {
        name: 'value',
        type: 'input',
        label: 'Value',
        isRequired: false,
        value: null,
    },
    userScope: {
        name: 'user-scope',
        type: 'dropdownSelector',
        label: 'User scope',
        isRequired: true,
        validate: Boolean,
        options: USER_SCOPE_DROPDOWN_OPTIONS,
    },
    userId: {
        name: 'user-id',
        type: 'input',
        label: 'User ID',
        isRequired: false,
        validate: Boolean,
        value: null,
    },
};