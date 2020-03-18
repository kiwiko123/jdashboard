import React from 'react';
import PropTypes from 'prop-types';
import Banner from '../../common/components/Banner';
import classnames from 'classnames';

import '../styles/ScrabbleAlerts.css';

const ScrabbleAlerts = ({ alerts, className }) => {
    const banners = alerts.map(alert => (
        <Banner
            key={alert.id}
            className="scrabble-alert"
            type={alert.type}
        >
            {alert.message}
        </Banner>
    ));

    const componentClassName = classnames('ScrabbleAlerts', className);
    return (
        <div className={componentClassName}>
            {banners}
        </div>
    );
};

ScrabbleAlerts.propTypes = {
    alerts: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.string.isRequired,
        type: PropTypes.string.isRequired,
        message: PropTypes.string.isRequired,
    })),
    className: PropTypes.string,
};

ScrabbleAlerts.defaultProps = {
    alerts: [],
    className: null,
};

export default ScrabbleAlerts;