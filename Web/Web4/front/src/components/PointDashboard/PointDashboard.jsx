import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useDispatch, useSelector} from "react-redux";
import {setState} from "../../redux/slices/stateSlice.js";
import {addPoint, setPoints} from "../../redux/slices/pointsSlice.js";

// -------------------------------------------------------------------------------------------------------- //

import {Text} from "../Caption/Text.jsx";
import {PointForm} from "./PointForm.jsx";
import {Plane} from "./Plane.jsx";
import {Archive} from "./Archive.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {axiosAddPoint, axiosGetPoints} from "../../utils/axios.js";
import {status, toPointDTO} from "../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./PointDashboard.module.scss";
import classNames from "classnames";
import {useCallback, useEffect} from "react";

// -------------------------------------------------------------------------------------------------------- //

export function PointDashboard({ui = uiDefault, ...rest}) {

    const dispatch = useDispatch();

    const state = useSelector(st => st.state);
    const handleStateChange = st => dispatch(setState(st));

    const handlePointAdd = useCallback(pnt => dispatch(addPoint(pnt)), [dispatch]);
    const handlePointsChange = useCallback(pnt => dispatch(setPoints(pnt)), [dispatch]);

    const planeLength = useSelector(st => st.planeLength);

    async function handleSubmit(st, formData = null) {
        handleStateChange(st);
        if (st === status.OK) axiosAddPoint(toPointDTO(formData), handlePointAdd);
    }

    useEffect(() => axiosGetPoints(handlePointsChange), [handlePointsChange]);

    return (
        <div className={classNames(styles.wrapper, ui.class.wrap)}
             style={ui.style.wrap}>

            {state && <Text value={state}
                            className={classNames(styles.error, ui.class.error)}
                            style={ui.style.error}/>}

            <PointForm handler={handleSubmit}
                       {...rest}/>

            <Plane length={planeLength}
                   XMin={-3} XMax={5}
                   YMin={-3} YMax={5}
                   handler={handleSubmit}/>

            <Archive name="Архив"
                     ui={{...uiDefault, style: {wrap: {maxHeight: planeLength}}}}/>

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
