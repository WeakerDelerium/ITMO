import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {Button} from "../Button.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Button.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function FrameButton({value, className, style, ...rest}) {
    return (
        <Button value={value}
                className={classNames(styles.frame, className)}
                style={style}
                {...rest}/>
    );
}

// -------------------------------------------------------------------------------------------------------- //

FrameButton.propTypes = {
    value: PropTypes.string.isRequired,
    className: PropTypes.string,
    style: PropTypes.object,
}
