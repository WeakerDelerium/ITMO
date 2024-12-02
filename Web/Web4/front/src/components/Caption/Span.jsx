import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Caption.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Span({value, className, style, ...rest}) {
    return (
        <span className={classNames(styles.span, className)}
              style={style}
              {...rest}>{value}</span>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Span.propTypes = {
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    className: PropTypes.string,
    style: PropTypes.object,
}
