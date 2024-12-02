import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {Heading} from "../Caption/Heading.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from './PointDashboard.module.scss';
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Archive({name, ui = uiDefault, ...rest}) {
    return (
        <div className={classNames(styles.twrapper, ui.class.wrap)}
             style={ui.style.wrap}>

            <Heading value={name}
                     className={classNames(styles.heading, ui.class.heading)}
                     style={ui.style.heading}/>

            <table className={classNames(styles.table, ui.class.table)}
                   style={ui.style.table}
                   {...rest}>

                <thead className={classNames(styles.thead, ui.class.thead)}
                       style={ui.style.thead}>

                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Hit</th>
                    <th>Exec</th>
                </tr>

                </thead>

                <tbody className={classNames(styles.tbody, ui.class.tbody)}
                       style={ui.style.tbody}>

                {/*<tr>*/}
                {/*    <td></td>*/}
                {/*</tr>*/}

                </tbody>

            </table>

        </div>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Archive.propTypes = {
    name: PropTypes.string.isRequired,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            wrap: PropTypes.string,
            heading: PropTypes.string,
            table: PropTypes.string,
            thead: PropTypes.string,
            tbody: PropTypes.string,
        }),
        style: PropTypes.shape({
            wrap: PropTypes.object,
            heading: PropTypes.object,
            table: PropTypes.object,
            thead: PropTypes.object,
            tbody: PropTypes.object,
        }),
    }),
}
