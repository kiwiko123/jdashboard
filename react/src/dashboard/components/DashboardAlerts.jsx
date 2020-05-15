import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Banner from '../../common/components/Banner';
import IconButton from '../../common/components/IconButton';

import '../styles/DashboardAlerts.css';

function makeAlertBanner({ alert, removeAlert }) {
    const dismissButton = alert.dismissable && (
        <IconButton
            className="dismiss"
            fontAwesomeClassName="fas fa-times"
            onClick={() => removeAlert(alert.id)}
        />
    );

    return (
        <Banner
            key={alert.id}
            className="dashboard-alert"
            type={alert.bannerType}
        >
            <>
                <div className="message">
                    {alert.message}
                </div>
                {dismissButton}
            </>
        </Banner>
    );
}

const DashboardAlerts = ({ alerts, className, removeAlert }) => {
    const componentClassName = classnames('DashboardAlerts', className);
    const banners = alerts.map(alert => makeAlertBanner({ alert, removeAlert }));

    return (
        <div className={componentClassName}>
            {banners}
        </div>
    );
};

DashboardAlerts.propTypes = {
    alerts: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.string.isRequired,
        bannerType: PropTypes.string.isRequired,
        message: PropTypes.string.isRequired,
        dismissable: PropTypes.bool, // default false
    })),
    className: PropTypes.string,
    removeAlert: PropTypes.func,
};

DashboardAlerts.defaultProps = {
    alerts: [],
    className: null,
    removeAlert: () => {},
};

export default DashboardAlerts;