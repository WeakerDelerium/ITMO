import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Form.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Form({children, className, style, ...rest}) {
    return (
        <form className={classNames(styles.form, className)}
              style={style}
              {...rest}>{children}</form>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Form.propTypes = {
    children: PropTypes.node.isRequired,
    className: PropTypes.string,
    style: PropTypes.object,
}