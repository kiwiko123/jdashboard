import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ClickTarget from '../../common/components/ClickTarget';

import '../../common/styles/colors.css';


export default class TextCell extends Component {

    static propTypes = {
        ...ClickTarget.propTypes,
        text: PropTypes.string,
    };

    static defaultProps = {
        ...ClickTarget.defaultProps,
        text: null,
    };

    render() {
        return (
            <ClickTarget
                className={this.props.className}
                onClick={this.props.onClick}
                draggable={this.props.draggable}
                onDragStart={this.props.onDragStart}
                onDragOver={this.props.onDragOver}
                onDrop={this.props.onDrop}
            >
                <span className="color-white">
                    {this.props.text}
                </span>
            </ClickTarget>
        );
    }
}