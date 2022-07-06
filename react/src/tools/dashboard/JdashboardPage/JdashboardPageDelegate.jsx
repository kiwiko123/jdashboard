import React from 'react';
import PropTypes from 'prop-types';
import { goTo } from 'common/js/urltools';
import logger from 'tools/monitoring/logging';
import useRequiredConditions from './util/useRequiredConditions';
import conditionsPropType from '../conditions/conditionsPropType';
import JdashboardResolvingPage from './JdashboardResolvingPage';
import JdashboardPage from './JdashboardPage';

const JdashboardPageDelegate = (props) => {
    const resolvedStatus = useRequiredConditions(props.requiredConditions);
    switch (resolvedStatus) {
        case 'success':
            return (<JdashboardPage {...props} />);
        case 'resolving':
            return (<JdashboardResolvingPage />);
        case 'failed':
            goTo('/not-found');
            return null;
        default:
            logger.error(`Unexpected resolved status from required conditions: ${resolvedStatus}`);
            goTo('/not-found');
            return null;
    }
}

JdashboardPageDelegate.propTypes = {
    ...JdashboardPage.propTypes,

    // If any of the conditions resolve to a falsy value, the page will be redirected to `/not-found`.
    requiredConditions: PropTypes.arrayOf(conditionsPropType),
};

JdashboardPageDelegate.defaultProps = {
    ...JdashboardPage.defaultProps,
    requiredConditions: [],
};

export default JdashboardPageDelegate;