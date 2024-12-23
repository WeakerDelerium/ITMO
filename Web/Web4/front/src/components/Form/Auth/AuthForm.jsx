import PropTypes from "prop-types";
import {uiDefault} from "../../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useEffect} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {useDispatch, useSelector} from "react-redux";
import {resetState, setState} from "../../../redux/slices/stateSlice.js";
import {setAuth} from "../../../redux/slices/authSlice.js";

// -------------------------------------------------------------------------------------------------------- //

import {useNavigate} from "react-router-dom";

// -------------------------------------------------------------------------------------------------------- //

import {Form} from "../Form.jsx";
import {FrameButton} from "../../Button/Frame/FrameButton.jsx";
import {Text} from "../../Caption/Text.jsx";
import {Heading} from "../../Caption/Heading.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {status} from "../../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Form.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function AuthForm({name, to, handler, children, ui = uiDefault, ...rest}) {

    const dispatch = useDispatch();

    const handleAuthChange = isAuth => dispatch(setAuth(isAuth));

    const state = useSelector(st => st.state);
    const handleStateChange = st => dispatch(setState(st));
    const handleStateReset = () => {dispatch(resetState())};

    const setPage = useNavigate();

    function handleRedirect(st) {
        if (st === status.OK) {
            handleAuthChange(true);
            setPage(to);
        }
    }

    async function handleClick() {
        const st = await handler();
        handleStateChange(st);
        handleRedirect(st);
    }

    useEffect(handleStateReset, [dispatch]);

    return (
        <div className={classNames(styles.auth, ui.class.wrap)}
             style={ui.style.wrap}>

            <Heading value={name}
                     className={classNames(styles.header, ui.class.heading)}
                     style={ui.style.heading}/>

            <Form className={ui.class.form}
                  style={ui.style.form}
                  {...rest}>{children}</Form>

            {state && <Text value={state}
                            className={classNames(styles.error, ui.class.error)}
                            style={ui.style.error}/>}

            <FrameButton value="Авторизоваться"
                         className={classNames(styles.btn, ui.class.btn)}
                         style={ui.style.btn}
                         onClick={handleClick}/>

        </div>
    );

}

// -------------------------------------------------------------------------------------------------------- //

AuthForm.propTypes = {
    name: PropTypes.string.isRequired,
    to: PropTypes.string.isRequired,
    handler: PropTypes.func.isRequired,
    children: PropTypes.node,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            wrap: PropTypes.string,
            heading: PropTypes.string,
            form: PropTypes.string,
            error: PropTypes.string,
            btn: PropTypes.string,
        }),
        style: PropTypes.shape({
            wrap: PropTypes.object,
            heading: PropTypes.object,
            form: PropTypes.object,
            error: PropTypes.object,
            btn: PropTypes.object,
        }),
    }),
}
