import PropTypes from 'prop-types';
import featureFlagRulePropTypeShape from './featureFlagRulePropTypeShape';

export default {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    isOnForMe: PropTypes.bool.isRequired,
    isOnForPublic: PropTypes.bool.isRequired,
    createdDate: PropTypes.string.isRequired,
    lastUpdatedDate: PropTypes.string.isRequired,
    rules: PropTypes.arrayOf(PropTypes.shape(featureFlagRulePropTypeShape).isRequired).isRequired,
};