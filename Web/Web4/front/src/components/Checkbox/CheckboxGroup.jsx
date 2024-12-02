import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {Checkbox} from "./Checkbox.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Checkbox.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function CheckboxGroup({name, rows, columns, values, onChange, ui = uiDefault, ...rest}) {

    const [selectedValue, setSelectedValue] = useState(null);
    const handleSelValueChange = selVal => setSelectedValue(() => selVal);

    function handleChange(event) {
        let val = Number(event.target.value);
        if (val === selectedValue) event.target.value = val = null;
        if (onChange) onChange(event);
        handleSelValueChange(val);
    }

    const gridStyles = {grid: `repeat(${rows}, 1fr) / repeat(${columns}, 1fr)`};

    return (
        <ul className={classNames(styles.group, ui.class.group)}
            style={{...gridStyles, ...ui.style.group}}>

            {values.map((val, i) => (

                <li key={i}
                    className={classNames(styles.item, ui.class.item)}
                    style={ui.style.item}>

                    <Checkbox
                        name={name}
                        value={val}
                        checked={selectedValue === val}
                        onChange={handleChange}
                        {...rest}/>

                </li>

            ))}

        </ul>
    );

}

// -------------------------------------------------------------------------------------------------------- //

CheckboxGroup.propTypes = {
    name: PropTypes.string.isRequired,
    rows: PropTypes.number.isRequired,
    columns: PropTypes.number.isRequired,
    values: PropTypes.arrayOf(PropTypes.number).isRequired,
    onChange: PropTypes.func,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            group: PropTypes.string,
            item: PropTypes.string,
        }),
        style: PropTypes.shape({
            group: PropTypes.object,
            item: PropTypes.object,
        }),
    }),
}
