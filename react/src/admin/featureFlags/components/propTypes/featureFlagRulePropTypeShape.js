import PropTypes from 'prop-types';

export default {
    featureFlagRuleId: PropTypes.number.isRequired,
    featureFlagId: PropTypes.number.isRequired,
    flagStatus: PropTypes.string.isRequired,
    scope: PropTypes.string.isRequired,
    userId: PropTypes.number,
    flagValue: PropTypes.string,
    startDate: PropTypes.string.isRequired,
    endDate: PropTypes.string,
    isOn: PropTypes.bool.isRequired,
};