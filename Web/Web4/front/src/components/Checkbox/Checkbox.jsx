import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {Span} from "../Caption/Span.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Checkbox.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Checkbox({name, value, checked = false, ui = uiDefault, ...rest}) {
    return (
        <label>

            <input type="checkbox"
                   name={name}
                   value={value}
                   checked={checked}
                   className={classNames(styles.checkbox, ui.class.checkbox)}
                   style={ui.style.checkbox}
                   {...rest}/>

            <Span className={classNames(styles.style, ui.class.style)}
                  style={ui.style.style}/>

            <Span value={value}
                  className={classNames(styles.text, ui.class.text)}
                  style={ui.style.text}/>

        </label>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Checkbox.propTypes = {
    name: PropTypes.string.isRequired,
    value: PropTypes.number.isRequired,
    checked: PropTypes.bool,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            checkbox: PropTypes.string,
            style: PropTypes.string,
            text: PropTypes.string,
        }),
        style: PropTypes.shape({
            checkbox: PropTypes.object,
            style: PropTypes.object,
            text: PropTypes.object,
        }),
    }),
}
