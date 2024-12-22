import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {Text} from "../Caption/Text.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Layout.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Footer({ui = uiDefault, ...rest}) {
    return (
        <div className={classNames(styles.footer, ui.class.outer)}
             style={ui.style.outer}
             {...rest}>

            <div className={classNames(styles.container, ui.class.container)}
                 style={ui.style.container}>

                <div className={classNames(styles.footer__inner, ui.class.inner)}
                     style={ui.style.inner}>

                    <Text value="Учусь на третьем курсе в ИТОМ. В свободное время унижаю пекусов."
                          className={ui.class.text}
                          style={ui.style.text}/>

                </div>

            </div>

        </div>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Footer.propTypes = {
    ui: PropTypes.shape({
        class: PropTypes.shape({
            outer: PropTypes.string,
            container: PropTypes.string,
            inner: PropTypes.string,
            text: PropTypes.string,
        }),
        style: PropTypes.shape({
            outer: PropTypes.object,
            container: PropTypes.object,
            inner: PropTypes.object,
            text: PropTypes.object,
        }),
    }),
}
