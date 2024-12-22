import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Caption.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Text({value, className, style, ...rest}) {
    return (
        <p className={classNames(styles.text, className)}
           style={style}
           {...rest}>{value}</p>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Text.propTypes = {
    value: PropTypes.string.isRequired,
    className: PropTypes.string,
    style: PropTypes.object,
}
