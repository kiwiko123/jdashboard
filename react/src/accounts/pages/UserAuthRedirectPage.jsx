import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { getUrlParameters, goTo } from 'common/js/urltools';

import './UserAuthRedirectPage.css';

const UserAuthRedirectPage = ({
    text, redirectWaitSeconds, className,
}) => {
    useEffect(() => {
        const { redirect } = getUrlParameters();
        if (!redirect) {
            goTo('/not-found');
            return;
        }
        setTimeout(() => {
            goTo(redirect);
        }, redirectWaitSeconds * 1000);
    }, []);
    const divClassName = classnames('UserAuthRedirectPage', className);

    return (
        <div className={divClassName}>
            <div className="text">
                {text}
            </div>
            <i className="fas fa-circle-notch fa-spin" />
        </div>
    );
};

UserAuthRedirectPage.propTypes = {
    text: PropTypes.string.isRequired,
    redirectWaitSeconds: PropTypes.number,
    className: PropTypes.string,
};

UserAuthRedirectPage.defaultProps = {
    redirectWaitSeconds: 0,
    className: null,
};

export default UserAuthRedirectPage;