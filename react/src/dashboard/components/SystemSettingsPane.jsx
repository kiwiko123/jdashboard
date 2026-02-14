import React, { useContext } from 'react';
import classnames from 'classnames';
import StandardButton from 'ui/StandardButton';
import GlassPane from 'ui/styles/glass/GlassPane';
import AppearanceContext from 'ui/appearance/AppearanceContext';
import Themes from 'ui/appearance/theme/Themes';

import './SystemSettingsPane.css';

const SettingButton = ({
    className, fontAwesomeClassName, selected, subtitle, onClick, disabled,
}) => {
    const divClassName = classnames('SettingButton', 'clear', className, {
        selected,
    });
    return (
        <div className={divClassName}>
            <StandardButton
                fontAwesomeClassName={fontAwesomeClassName}
                onClick={onClick}
                disabled={disabled}
            />
            <label className="subtitle">{subtitle}</label>
        </div>
    );
};

const SystemSettingsPane = ({
    className,
}) => {
    const { theme, themeClassName, toggleTheme } = useContext(AppearanceContext);
    const isLightTheme = theme === Themes.light;
    const isDarkTheme = theme === Themes.dark;

    const divClassName = classnames('SystemSettingsPane', themeClassName)

    return (
        <div className={divClassName}>
            <div className="section theme">
                <h3 className="section-title">Theme</h3>
                <div className="buttons">
                    <SettingButton
                        className="immersive"
                        fontAwesomeClassName="fas fa-sun"
                        onClick={toggleTheme}
                        selected={isLightTheme}
                        disabled={isLightTheme}
                        subtitle="Light"
                    />
                    <SettingButton
                        className="immersive"
                        fontAwesomeClassName="fas fa-moon"
                        onClick={toggleTheme}
                        selected={isDarkTheme}
                        disabled={isDarkTheme}
                        subtitle="Dark"
                    />
                </div>
            </div>
        </div>
    );
};

export default SystemSettingsPane;