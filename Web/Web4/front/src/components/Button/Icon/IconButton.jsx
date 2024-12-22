import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {Button} from "../Button.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Button.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function IconButton({path, className, style, ...rest}) {

    const iconStyle = {
        WebkitMaskImage: `url(${path})`,
        maskImage: `url(${path})`,
    }

    return (
        <Button className={classNames(styles.icon, className)}
                style={{...iconStyle, ...style}}
                {...rest}/>
    );

}

// -------------------------------------------------------------------------------------------------------- //

IconButton.propTypes = {
    path: PropTypes.string.isRequired,
    value: PropTypes.oneOf([undefined]),
    className: PropTypes.string,
    style: PropTypes.object,
}