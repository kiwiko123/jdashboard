import React, { useContext } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { Styles } from 'ui/styles/StyleProps';
import AppearanceContext from 'ui/appearance/AppearanceContext';
import Themes from 'ui/appearance/theme/Themes';
import StandardSlideOverPane from './StandardSlideOverPane';
import GlassSlideOverPane from './GlassSlideOverPane';

const SlideOverPane = (props) => {
    const { style, themeClassName } = useContext(AppearanceContext);
    const className = classnames(props.className, themeClassName);

    let Component;
    switch (style) {
        case Styles.glass:
            Component = GlassSlideOverPane;
            break;
        default:
            Component = StandardSlideOverPane;
    }

    return (
        <Component
            {...props}
            className={className}
        />
    );
};

SlideOverPane.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
    expanded: PropTypes.bool,
    openFrom: PropTypes.oneOf(['side', 'top', 'bottom']),
};

SlideOverPane.defaultProps = {
    children: null,
    className: null,
    expanded: true,
    openFrom: 'side',
};

export default SlideOverPane;