import PropTypes from "prop-types";
import {uiDefault} from "../../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from 'react';

// -------------------------------------------------------------------------------------------------------- //

import {Input} from "../Input.jsx";
import {IconButton} from "../../Button/Icon/IconButton.jsx";

// -------------------------------------------------------------------------------------------------------- //

import show from "../../../assets/eye-password-show.svg";
import hide from "../../../assets/eye-password-hide.svg";

// -------------------------------------------------------------------------------------------------------- //

import {passwordPattern, sliceOutPattern, validatePassword} from "../../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Input.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function PasswordInput({onValidate, onChange, ui = uiDefault, ...rest}) {

    const [visible, setVisible] = useState(false);
    const [value, setValue] = useState("");

    const handleVisChange = () => setVisible(!visible);
    const handleValueChange = val => setValue(() => val);

    function handleChange(event) {
        const val = sliceOutPattern(event, passwordPattern)
        if (onValidate) onValidate(validatePassword(val));
        if (onChange) onChange(event);
        handleValueChange(val);
    }

    return (
        <div className={classNames(styles.wrapper, ui.class.wrap)}
             style={ui.style.wrap}>

            <Input value={value}
                   type={visible ? "text" : "password"}
                   className={ui.class.input}
                   style={ui.style.input}
                   onChange={handleChange} {...rest}/>

            <div className={classNames(styles.eye, ui.class.icon)}
                 style={ui.style.icon}>

                <IconButton path={String(visible ? hide : show)}
                            className={styles.icon}
                            onClick={handleVisChange}/>

            </div>

        </div>
    );

}

// -------------------------------------------------------------------------------------------------------- //

PasswordInput.propTypes = {
    onValidate: PropTypes.func,
    onChange: PropTypes.func,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            wrap: PropTypes.string,
            input: PropTypes.string,
            icon: PropTypes.string,
        }),
        style: PropTypes.shape({
            wrap: PropTypes.object,
            input: PropTypes.object,
            icon: PropTypes.object,
        }),
    }),
}
