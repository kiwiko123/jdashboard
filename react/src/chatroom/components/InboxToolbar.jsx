import React, { useState } from 'react';
import PropTypes from 'prop-types';
import IconButton from '../../common/components/IconButton';
import Modal from '../../common/components/Modal';

import './InboxToolbar.css';

const InboxToolbar = () => {
    const [shouldShowComposeModal, setShouldShowComposeModal] = useState(false);
    return (
        <div className="InboxToolbar">
            <IconButton
                variant="light"
                fontAwesomeClassName="fas fa-plus-circle"
                onClick={() => setShouldShowComposeModal(true)}
            />
            <Modal
                isOpen={shouldShowComposeModal}
                size="large"
            >
                Test
            </Modal>
        </div>
    );
};

InboxToolbar.propTypes = {

};

export default InboxToolbar;