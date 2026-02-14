import React, { useContext } from 'react';
import classnames from 'classnames';
import StandardButton from './StandardButton';
import GlassStandardButton from './GlassStandardButton';
import { Styles } from 'ui/styles/StyleProps';
import AppearanceContext from 'ui/appearance/AppearanceContext';

const Button = (props) => {
    const { style, themeClassName } = useContext(AppearanceContext);
    const className = classnames(props.className, themeClassName);

    let ButtonComponent;
    switch (style) {
        case Styles.glass:
            ButtonComponent = GlassStandardButton;
            break;
        default:
            ButtonComponent = StandardButton;
    }

    return (
        <ButtonComponent
            {...props}
            className={className}
        />
    );
}

export default Button;