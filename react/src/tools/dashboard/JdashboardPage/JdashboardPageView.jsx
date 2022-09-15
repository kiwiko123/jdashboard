import React, { useCallback } from 'react';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router';
import PerimeterPullView from 'ui/views/components/PerimeterPullView';
import LoadingIndicator from 'ui/LoadingIndicator';

const RightArrow = () => (<i className="fas fa-arrow-right interactive-panel-indicator" />);
const LeftArrow = () => (<i className="fas fa-arrow-left interactive-panel-indicator" />);

const JdashboardPageView = ({
    children, className,
}) => {
    const history = useHistory();
    const refreshPage = useCallback(() => {
        window.location.reload();
    }, []);
    const browserBack = useCallback(() => {
        history.goBack();
    }, [history]);
    const browserForward = useCallback(() => {
        history.goForward();
    }, [history]);

    return (
        <PerimeterPullView
            className={className}
            onPullFromTop={refreshPage}
            onPullFromLeft={browserBack}
            onPullFromRight={browserForward}
            interactiveHeader={<LoadingIndicator size="2x" />}
            interactiveLeftPane={<LeftArrow />}
            interactiveRightPane={<RightArrow />}
        >
            {children}
        </PerimeterPullView>
    );
};

JdashboardPageView.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
};

JdashboardPageView.defaultProps = {
    children: null,
    className: null,
};

export default JdashboardPageView;