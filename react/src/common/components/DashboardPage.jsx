import React, { Component } from 'react';
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

export default class DashboardPage extends Component {
    static propTypes = {
        children: PropTypes.node.isRequired,
        className: PropTypes.string,
        title: PropTypes.string.isRequired,
        appId: PropTypes.string.isRequired,
        history: PropTypes.shape({
            push: PropTypes.func.isRequired,
        }).isRequired,
    };

    static defaultProps = {
        className: null,
    };

    render() {
        const className = classnames('DashboardPage', this.props.className);
        const headerButtons = createHeaderButtons({
            history: this.props.history,
            appId: this.props.appId,
        });

        return (
            <div className={className}>
                <div className="header row">
                    <h1 className="color-white">
                        {this.props.title}
                    </h1>
                </div>
                <div className="body">
                    {this.props.children}
                </div>
            </div>
        );
    }
}