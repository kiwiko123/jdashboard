import React from 'react';
import PropTypes from 'prop-types';
import DashboardPage from '../../dashboard/components/DashboardPage';
import HomeContent from '../components/HomeContent';

const HomePage = ({ history }) => {
    return (
        <DashboardPage
            className="HomePage"
            title="Home"
            appId="home"
        >
            <HomeContent />
        </DashboardPage>
    );
};

HomePage.propTypes = {
    history: PropTypes.shape({
        push: PropTypes.func.isRequired,
    }).isRequired,
};

export default HomePage;