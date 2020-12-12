import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { omit } from 'lodash';
import Modal from './Modal';

import './TitleModal.css';

const TitleModal = ({
    title,

    // Modal props
    ...props,
}) => {
    const divClassName = classnames('TitleModal', props.className);
    const modalProps = omit(props, ['className', 'children']);
    return (
        <Modal
            {...modalProps}
            className={divClassName}
        >
            <div className="title">
                {title}
            </div>
            <div className="content">
                {props.children}
            </div>
        </Modal>
    );
};

TitleModal.propTypes = {
    ...Modal.propTypes,
    title: PropTypes.string.isRequired,
};

TitleModal.defaultProps = {
    ...Modal.defaultProps,
};

export default TitleModal;