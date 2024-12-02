import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {Text} from "./Text.jsx";
import {Link} from "react-router-dom";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Caption.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function RedirectCaption({desc, link, url, ui = uiDefault, ...rest}) {
    return (
        <div className={classNames(styles.wrapper, ui.class.wrap)}
             style={ui.style.wrap}
             {...rest}>

            <Text value={desc}
                  className={ui.class.desc}
                  style={ui.style.desc}/>

            <Link to={url}
                  className={classNames(styles.link, ui.class.link)}
                  style={ui.style.link}>{link}</Link>

        </div>
    );
}

// -------------------------------------------------------------------------------------------------------- //

RedirectCaption.propTypes = {
    desc: PropTypes.string.isRequired,
    link: PropTypes.string.isRequired,
    url: PropTypes.string.isRequired,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            wrap: PropTypes.string,
            desc: PropTypes.string,
            link: PropTypes.string,
        }),
        style: PropTypes.shape({
            wrap: PropTypes.object,
            desc: PropTypes.object,
            link: PropTypes.object,
        }),
    }),
}
