import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Input.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Input({type, className, style, ...rest}) {
    return (
        <input type={type}
               className={classNames(styles.input, className)}
               style={style}
               autoComplete="off"
               onPaste={e => e.preventDefault()}
               {...rest}/>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Input.propTypes = {
    type: PropTypes.string.isRequired,
    className: PropTypes.string,
    style: PropTypes.object,
}
