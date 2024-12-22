import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from 'react';

// -------------------------------------------------------------------------------------------------------- //

import {Input} from "../Input.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {loginPattern, sliceOutPattern, validateLogin} from "../../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

export function UsernameInput({onValidate, onChange, className, style, ...rest}) {

    const [value, setValue] = useState("");

    const handleValueChange = val => setValue(() => val);

    function handleChange(event) {
        const val = sliceOutPattern(event, loginPattern);
        if (onValidate) onValidate(validateLogin(val));
        if (onChange) onChange(event);
        handleValueChange(val);
    }

    return (
        <Input type="text"
               value={value}
               className={className}
               style={style}
               onChange={handleChange}
               {...rest}/>
    );

}

// -------------------------------------------------------------------------------------------------------- //

UsernameInput.propTypes = {
    onValidate: PropTypes.func,
    onChange: PropTypes.func,
    className: PropTypes.string,
    style: PropTypes.object,
}
