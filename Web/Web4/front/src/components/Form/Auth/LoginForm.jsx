import PropTypes from "prop-types";
import {uiDefault} from "../../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {useDispatch, useSelector} from "react-redux";
import {resetState} from "../../../redux/slices/stateSlice.js";

// -------------------------------------------------------------------------------------------------------- //

import {AuthForm} from "./AuthForm.jsx";
import {Text} from "../../Caption/Text.jsx";
import {UsernameInput} from "../../Input/Auth/UsernameInput.jsx";
import {PasswordInput} from "../../Input/Auth/PasswordInput.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {loginFormDataPattern, formDataChange, status, toCredentialsDTO} from "../../../utils/form.js";
import {axiosLogin} from "../../../utils/axios.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Form.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function LoginForm({to, ui = uiDefault, ...rest}) {

    const dispatch = useDispatch();
    const state = useSelector(st => st.state);
    const handleStateReset = () => dispatch(resetState());

    const [formData, setFormData] = useState(loginFormDataPattern);

    function handleChange(event) {
        if (state) handleStateReset();
        formDataChange(event, formData, setFormData);
    }

    async function handleSubmit() {
        const username = formData.username;
        const password = formData.password;

        return (!username) ? status.EMPTY_USERNAME :
               (!password) ? status.EMPTY_PASSWORD :
               axiosLogin(toCredentialsDTO(formData), status.OK, status.AUTHENTICATION, status.UNEXPECTED);
    }

    return (
        <AuthForm name="Войти"
                  to={to}
                  handler={handleSubmit}
                  {...rest}>

            <div className={classNames(styles.field, ui.class.field)}
                 style={ui.style.field}>

                <Text value='Введите имя'
                      className={classNames(styles.label, ui.class.loginLabel)}
                      style={ui.style.loginLabel}/>

                <UsernameInput name="username"
                               className={ui.class.loginInput}
                               style={ui.style.loginInput}
                               onChange={handleChange}/>

            </div>

            <div className={classNames(styles.field, ui.class.field)}
                 style={ui.style.field}>

                <Text value='Введите пароль'
                      className={classNames(styles.label, ui.class.passwordLabel)}
                      style={ui.style.passwordLabel}/>

                <PasswordInput name="password"
                               className={ui.class.passwordInput}
                               style={ui.style.passwordInput}
                               onChange={handleChange}/>

            </div>

        </AuthForm>
    );

}

// -------------------------------------------------------------------------------------------------------- //

LoginForm.propTypes = {
    to: PropTypes.string.isRequired,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            field: PropTypes.string,
            loginLabel: PropTypes.string,
            loginInput: PropTypes.string,
            passwordLabel: PropTypes.string,
            passwordInput: PropTypes.string,
        }),
        style: PropTypes.shape({
            field: PropTypes.object,
            loginLabel: PropTypes.object,
            loginInput: PropTypes.object,
            passwordLabel: PropTypes.object,
            passwordInput: PropTypes.object,
        }),
    }),
}
