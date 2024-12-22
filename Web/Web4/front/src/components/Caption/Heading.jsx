import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Caption.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Heading({value, className, style, ...rest}) {
    return (
        <h3 className={classNames(styles.heading, className)}
            style={style}
            {...rest}>{value}</h3>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Heading.propTypes = {
    value: PropTypes.string.isRequired,
    className: PropTypes.string,
    style: PropTypes.object,
}
