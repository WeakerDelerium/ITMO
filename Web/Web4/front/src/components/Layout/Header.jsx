import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {Heading} from "../Caption/Heading.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Layout.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Header({ui = uiDefault, ...rest}) {
    return (
        <div className={classNames(styles.header, ui.class.outer)}
             style={ui.style.outer}
             {...rest}>

            <div className={classNames(styles.container, ui.class.container)}
                 style={ui.style.container}>

                <div className={classNames(styles.header__inner, ui.class.inner)}
                     style={ui.style.inner}>

                    <Heading value="Алексеев Андрей"
                             className={ui.class.student}
                             style={ui.style.student}/>

                    <Heading value="P3219"
                             className={ui.class.group}
                             style={ui.style.group}/>

                    <Heading value="Вариант 92835"
                             className={ui.class.option}
                             style={ui.style.option}/>

                </div>

            </div>

        </div>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Header.propTypes = {
    ui: PropTypes.shape({
        class: PropTypes.shape({
            outer: PropTypes.string,
            container: PropTypes.string,
            inner: PropTypes.string,
            student: PropTypes.string,
            group: PropTypes.string,
            option: PropTypes.string,
        }),
        style: PropTypes.shape({
            outer: PropTypes.object,
            container: PropTypes.object,
            inner: PropTypes.object,
            student: PropTypes.object,
            group: PropTypes.object,
            option: PropTypes.object,
        }),
    }),
}
