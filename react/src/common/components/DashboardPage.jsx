import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Button from 'react-bootstrap/Button';

import '../styles/colors.css';
import '../styles/common.css';
import '../styles/DashboardPage.css';

const headerButtons = [
    {
        id: 'scrabble',
        label: 'Scrabble',
        onClick: history => history.push('/scrabble/play'),
    },
];

function createHeaderButtons({ history, appId }) {
    return headerButtons
        .filter(data => data.id !== appId)
        .map(data => (
            <Button
                key={data.id}
                className={`button ${data.id}`}
                variant="link"
                onClick={() => data.onClick(history)}
                disabled={data.id === appId}
            >
                {data.label}
            </Button>
    ));
}

const DashboardPage = ({
    children, className, title, appId, history,
}) => {
    useEffect(() => {
        document.title = title;
    });

    const pageClassName = classnames('DashboardPage', className);
    const headerButtons = createHeaderButtons({ history, appId });

    return (
        <div className={pageClassName}>
            <div className="header row">
                <h1 className="color-white">
                    {title}
                </h1>
                {headerButtons}
            </div>
            <div className="body">
                {children}
            </div>
        </div>
    );
}

DashboardPage.propTypes = {
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
    title: PropTypes.string,
    appId: PropTypes.string.isRequired,
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
};

DashboardPage.defaultProps = {
    className: null,
    title: 'Dashboard',
};

export default DashboardPage;