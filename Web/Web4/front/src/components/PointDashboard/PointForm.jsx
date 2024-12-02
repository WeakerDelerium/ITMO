import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {useDispatch, useSelector} from "react-redux";
import {setR} from "../../redux/slices/RSlice.js";
import {setState} from "../../redux/slices/StateSlice.js";

// -------------------------------------------------------------------------------------------------------- //

import {Form} from "../Form/Form.jsx";
import {Text} from "../Caption/Text.jsx";
import {NumberInput} from "../Input/Number/NumberInput.jsx";
import {CheckboxGroup} from "../Checkbox/CheckboxGroup.jsx";
import {FrameButton} from "../Button/Frame/FrameButton.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {pointFordDataPattern, formDataChange, validatePoint, status} from "../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./PointDashboard.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function PointForm({handler, ui = uiDefault, ...rest}) {

    const dispatch = useDispatch();

    const state = useSelector(st => st.state);
    const handleStateChange = st => dispatch(setState(st));

    const [formData, setFormData] = useState(pointFordDataPattern);

    function handleChange(event) {
        if (state === status.EMPTY_FIELDS) handleStateChange(status.OK);
        else if (state === status.EMPTY_R) handleStateChange(status.OK);
        formDataChange(event, formData, setFormData);
    }

    function handleRChange(event) {
        dispatch(setR(event.target.value));
        handleChange(event);
    }

    const handleSubmit = () => validatePoint(formData) ? handler(status.OK, formData) : handler(status.EMPTY_FIELDS);

    return (
        <div className={classNames(styles.fwrapper, ui.class.wrap)}
             style={ui.style.wrap}>

            <Form className={classNames(styles.form, ui.class.form)}
                  style={ui.style.form}
                  {...rest}>

                <div className={classNames(styles.field, ui.class.field)}
                     style={ui.style.field}>

                    <Text value="X"
                          className={ui.class.XLabel}
                          style={ui.style.XLabel}/>

                    <CheckboxGroup name="X"
                                   rows={3}
                                   columns={3}
                                   values={[-3, -2, -1, 0, 1, 2, 3, 4, 5]}
                                   onChange={handleChange}/>

                </div>

                <div className={classNames(styles.field, ui.class.field)}
                     style={ui.style.field}>

                    <Text value="Y"
                          className={ui.class.YLabel}
                          style={ui.style.YLabel}/>

                    <NumberInput name="Y"
                                 min={-3}
                                 max={5}
                                 className={classNames(styles.number, ui.class.YInput)}
                                 style={ui.style.YInput}
                                 onChange={handleChange}/>

                </div>

                <div className={classNames(styles.field, ui.class.field)}
                     style={ui.style.field}>

                    <Text value="R"
                          className={ui.class.RLabel}
                          style={ui.style.RLabel}/>

                    <CheckboxGroup name="R"
                                   rows={2}
                                   columns={3}
                                   values={[1, 2, 3, 4, 5]}
                                   onChange={handleRChange}/>

                </div>

            </Form>

            <FrameButton value="Отправить"
                         className={ui.class.btn}
                         style={ui.style.btn}
                         onClick={handleSubmit}/>

        </div>
    );

}

// -------------------------------------------------------------------------------------------------------- //

PointForm.propTypes = {
    handler: PropTypes.func.isRequired,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            wrap: PropTypes.string,
            form: PropTypes.string,
            field: PropTypes.string,
            XLabel: PropTypes.string,
            YLabel: PropTypes.string,
            YInput: PropTypes.string,
            RLabel: PropTypes.string,
            RInput: PropTypes.string,
            btn: PropTypes.string,
        }),
        style: PropTypes.shape({
            wrap: PropTypes.object,
            form: PropTypes.object,
            field: PropTypes.object,
            XLabel: PropTypes.object,
            XInput: PropTypes.object,
            YLabel: PropTypes.object,
            YInput: PropTypes.object,
            RLabel: PropTypes.object,
            RInput: PropTypes.object,
            btn: PropTypes.object,
        }),
    }),
};
