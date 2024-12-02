import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useDispatch, useSelector} from "react-redux";
import {setState} from "../../redux/slices/StateSlice.js";

// -------------------------------------------------------------------------------------------------------- //

import {Text} from "../Caption/Text.jsx";
import {PointForm} from "./PointForm.jsx";
import {Plane} from "./Plane.jsx";
import {Archive} from "./Archive.jsx";

// -------------------------------------------------------------------------------------------------------- //

// import {status} from "../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./PointDashboard.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function PointDashboard({ui = uiDefault, ...rest}) {

    const dispatch = useDispatch();

    const state = useSelector(st => st.state);
    const handleStateChange = st => dispatch(setState(st));

    function handleSubmit(st, formData = null) {
        handleStateChange(st);
        console.log(formData, st, state);
    }

    return (
        <div className={classNames(styles.wrapper, ui.class.wrap)}
             style={ui.style.wrap}>

            {state && <Text value={state}
                            className={classNames(styles.error, ui.class.error)}
                            style={ui.style.error}/>}

            <PointForm handler={handleSubmit}
                       {...rest}/>

            <Plane handler={handleSubmit}
                   length={600}/>

            <Archive name="Архив"/>

        </div>
    );

}

// -------------------------------------------------------------------------------------------------------- //

PointDashboard.propTypes = {
    ui: PropTypes.shape({
        class: PropTypes.shape({
            wrap: PropTypes.string,
            error: PropTypes.string,
        }),
        style: PropTypes.shape({
            wrap: PropTypes.object,
            error: PropTypes.object,
        }),
    }),
}
