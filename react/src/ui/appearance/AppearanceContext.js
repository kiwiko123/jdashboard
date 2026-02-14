import { createContext } from 'react';
import classnames from 'classnames';
import Themes from './theme/Themes';

function makeThemeClassName(theme) {
    switch (theme) {
        case Themes.light:
            return 'theme-light';
        case Themes.dark:
            return 'theme-dark';
        default:
            return null;
    }
}

export function makeAppearanceContext({ theme, toggleTheme }) {
    return {
        theme,
        themeClassName: makeThemeClassName(theme),
        toggleTheme,
        style: 'glass',
    };
}

function createInitialAppearanceContext() {
    const value = makeAppearanceContext({
        theme: Themes.dark,
        toggleTheme: () => {}, // Placeholder
    });
    return createContext(value);
}

export default createInitialAppearanceContext();