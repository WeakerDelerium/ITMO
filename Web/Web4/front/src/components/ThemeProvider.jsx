import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {useCallback, useEffect} from 'react';

// -------------------------------------------------------------------------------------------------------- //

import {useSelector, useDispatch} from 'react-redux';
import {setTheme} from "../redux/slices/themeSlice.js";

// -------------------------------------------------------------------------------------------------------- //

export function ThemeProvider({children}) {

    const dispatch = useDispatch();
    const theme = useSelector(state => state.theme.current);

    function compareSysTheme(event) {
        const newTheme = event.matches ? "dark" : "light";
        if (newTheme !== theme) dispatch(setTheme(newTheme));
    }

    function handleThemeChange() {
        document.documentElement.toggleAttribute("light-theme", theme === "light");
        document.documentElement.toggleAttribute("dark-theme", theme === "dark");

        const media = window.matchMedia("(prefers-color-scheme: dark)");
        handleSysThemeChange(media);

        media.addEventListener('change', handleSysThemeChange);
        return () => media.removeEventListener('change', handleSysThemeChange);
    }

    const handleSysThemeChange = useCallback(compareSysTheme, [theme, dispatch]);
    useEffect(handleThemeChange, [theme, dispatch, handleSysThemeChange]);

    return children;

}

// -------------------------------------------------------------------------------------------------------- //

ThemeProvider.propTypes = {
    children: PropTypes.node,
}
