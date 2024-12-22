import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Layout.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Content({children, ui = uiDefault, ...rest}) {
    return (
        <div className={classNames(styles.content, ui.class.outer)}
             style={ui.style.outer}
             {...rest}>

            <div className={classNames(styles.container, ui.class.container)}
                 style={ui.style.container}>

                <div className={classNames(styles.content__inner, ui.class.inner)}
                     style={ui.style.inner}>{children}</div>

            </div>

        </div>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Content.propTypes = {
    children: PropTypes.node,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            outer: PropTypes.string,
            container: PropTypes.string,
            inner: PropTypes.string,
        }),
        style: PropTypes.shape({
            outer: PropTypes.object,
            container: PropTypes.object,
            inner: PropTypes.object,
        }),
    }),
}
