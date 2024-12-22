import PropTypes from "prop-types";
import {uiDefault} from "../../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from 'react'

// -------------------------------------------------------------------------------------------------------- //

import {PasswordInput} from "./PasswordInput.jsx";
import {Text} from "../../Caption/Text.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {passwordPattern, sliceOutPattern, countStrengthScore, validatePassword} from "../../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Input.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

const diffList = ["Сложность", "Слабый", "Средний", "Сильный"];

// -------------------------------------------------------------------------------------------------------- //

export function StrengthPasswordInput({onValidate, onChange, ui = uiDefault, ...rest}) {

    const [value, setValue] = useState("");
    const handleValueChange = val => setValue(() => val);

    function handleChange(event) {
        const val = sliceOutPattern(event, passwordPattern)
        if (onValidate) onValidate(validatePassword(val));
        if (onChange) onChange(event);
        handleValueChange(val);
    }

    const score = countStrengthScore(value);

    const diff = diffList[Math.round(score / 2)];

    return (
        <div className={classNames(styles.wrapper, ui.class.wrap)}
             style={ui.style.wrap}>

            <PasswordInput onChange={handleChange}
                           {...rest}/>

            <div className={classNames(styles.meter, ui.class.meter)}
                 style={ui.style.meter}>

                <div className={classNames(styles.bar, ui.class.bar)}
                     style={ui.style.bar}>

                    <div className={classNames(styles.fill, ui.class.fill)}
                         style={{...{width: `${score / 6 * 100}%`}, ...ui.style.fill}}/>

                </div>

                <Text value={diff}
                      className={classNames(styles.diff, ui.class.diff)}
                      style={ui.style.diff}/>

            </div>

        </div>
    );

}

// -------------------------------------------------------------------------------------------------------- //

StrengthPasswordInput.propTypes = {
    onValidate: PropTypes.func,
    onChange: PropTypes.func,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            wrap: PropTypes.string,
            meter: PropTypes.string,
            bar: PropTypes.string,
            fill: PropTypes.string,
            diff: PropTypes.string,
        }),
        style: PropTypes.shape({
            wrap: PropTypes.object,
            meter: PropTypes.object,
            bar: PropTypes.object,
            fill: PropTypes.object,
            diff: PropTypes.object,
        }),
    }),
}
