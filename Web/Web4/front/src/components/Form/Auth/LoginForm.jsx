import PropTypes from "prop-types";
import {uiDefault} from "../../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {AuthForm} from "./AuthForm.jsx";
import {Text} from "../../Caption/Text.jsx";
import {LoginInput} from "../../Input/Auth/LoginInput.jsx";
import {PasswordInput} from "../../Input/Auth/PasswordInput.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {loginFormDataPattern, formDataChange, status} from "../../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Form.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function LoginForm({to, ui = uiDefault, ...rest}) {

    const [formData, setFormData] = useState(loginFormDataPattern);
    const handleChange = event => formDataChange(event, formData, setFormData);

    function handleSubmit() {
        // axios
        // axios check <- custom logs

        return status.OK;
    }

    return (
        <AuthForm name="Войти"
                  to={to}
                  handler={handleSubmit}
                  {...rest}>

            <div className={classNames(styles.field, ui.class.field)}
                 style={ui.style.field}>

                <Text value='Введите логин'
                      className={classNames(styles.label, ui.class.loginLabel)}
                      style={ui.style.loginLabel}/>

                <LoginInput name="login"
                            className={ui.class.loginInput}
                            style={ui.style.loginInput}
                            onChange={handleChange}/>

            </div>

            <div className={classNames(styles.field, ui.class.field)}
                 style={ui.style.field}>

                <Text value='Введите пароль'
                      className={classNames(styles.label, ui.class.passwdLabel)}
                      style={ui.style.passwdLabel}/>

                <PasswordInput name="passwd"
                               className={ui.class.passwdInput}
                               style={ui.style.passwdInput}
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
            passwdLabel: PropTypes.string,
            passwdInput: PropTypes.string,
        }),
        style: PropTypes.shape({
            field: PropTypes.object,
            loginLabel: PropTypes.object,
            loginInput: PropTypes.object,
            passwdLabel: PropTypes.object,
            passwdInput: PropTypes.object,
        }),
    }),
}
