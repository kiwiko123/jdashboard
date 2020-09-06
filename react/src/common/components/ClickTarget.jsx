import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import '../styles/ClickTarget.css';

export default class ClickTarget extends Component {
    static propTypes = {
        children: PropTypes.node,
        className: PropTypes.string,
        onClick: PropTypes.func,
        draggable: PropTypes.bool,
        onDrag: PropTypes.func,
        onDragStart: PropTypes.func,
        onDragOver: PropTypes.func,
        onDragEnd: PropTypes.func,
        onDrop: PropTypes.func,
        onClickOutside: PropTypes.func,
    };

    static defaultProps = {
        children: null,
        className: null,
        onClick: null,
        draggable: false,
        onDrag: null,
        onDragStart: null,
        onDragOver: event => event.preventDefault(),
        onDragEnd: null,
        onDrop: null,
        onClickOutside: () => {},
    };

    constructor(props) {
        super(props);
        this._wrapperRef = null;
        this._onClick = this._onClick.bind(this);
        this._onClickOutside = this._onClickOutside.bind(this);
        this._setWrapperRef = this._setWrapperRef.bind(this);
    }

    componentDidMount() {
        document.addEventListener('mousedown', this._onClickOutside);
    }

    componentWillUnmount() {
        document.removeEventListener('mousedown', this._onClickOutside);
    }

    render() {
        const className = classnames('ClickTarget', this.props.className, {
            clickable: this.props.onClick,
        });

        return (
            <div
                className={className}
                role="main"
                ref={this._setWrapperRef}
                onClick={this._onClick}
                draggable={this.props.draggable}
                onDrag={this.props.onDrag}
                onDragStart={this.props.onDragStart}
                onDragOver={this.props.onDragOver}
                onDragEnd={this.props.onDragEnd}
                onDrop={this.props.onDrop}
                style={this.props.style}
            >
                {this.props.children}
            </div>
        );
    }

    _setWrapperRef(node) {
        this._wrapperRef = node;
    }

    _onClickOutside(event) {
        if (this._wrapperRef && !this._wrapperRef.contains(event.target)) {
            this.props.onClickOutside(event);
        }
    }

    _onClick(event) {
        if (this.props.onClick) {
            this.props.onClick(event);
        }
    }
}