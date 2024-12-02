import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {Input} from "../Input.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {numberPattern, sliceOutPattern} from "../../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

export function NumberInput({min, max, onChange, className, style, ...rest}) {

    const [value, setValue] = useState("");

    function handleValueChange(event) {
        let val = sliceOutPattern(event, numberPattern);

        const last = event.target.selectionStart - 1;
        if (val < min || max < val) val = val.slice(0, last) + val.slice(last + 1);

        setValue(() => val);
    }

    function handleChange(event) {
        if (onChange) onChange(event);
        handleValueChange(event);
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

NumberInput.propTypes = {
    min: PropTypes.number.isRequired,
    max: PropTypes.number.isRequired,
    onChange: PropTypes.func,
    className: PropTypes.string,
    style: PropTypes.object,
}
