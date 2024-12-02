import PropTypes from "prop-types";
import {uiDefault} from "../../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useState} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {AuthForm} from "./AuthForm.jsx";
import {Text} from "../../Caption/Text.jsx";
import {LoginInput} from "../../Input/Auth/LoginInput.jsx";
import {PasswordInput} from "../../Input/Auth/PasswordInput.jsx";
import {StrengthPasswordInput} from "../../Input/Auth/StrengthPasswordInput.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {registerFormDataPattern, formDataChange, status} from "../../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Form.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function RegisterForm({to, ui = uiDefault, ...rest}) {

    const [formData, setFormData] = useState(registerFormDataPattern);

    const handleChange = event => formDataChange(event, formData, setFormData);

    const [loginValid, setLoginValid] = useState(false);
    const [passwdValid, setPasswdValid] = useState(false);

    const handleValidate = (valid, setter) => setter(valid);

    const handleLoginValidate = valid => handleValidate(valid, setLoginValid);
    const handlePasswdValidate = valid => handleValidate(valid, setPasswdValid);

    function handleSubmit() {
        const login = formData.login;
        const passwd = formData.passwd;
        const confirm = formData.confirm;

        let res = (!login) ? status.EMPTY_LOGIN :
                  (!passwd) ? status.EMPTY_PASSWORD :
                  (!loginValid) ? status.INVALID_LOGIN :
                  (!passwdValid) ? status.INVALID_PASSWORD :
                  (passwd !== confirm) ? status.MISMATCH_PASSWORDS :
                  status.OK;

        // axios
        // axios check <- custom log

        return res;
    }

    return (
        <AuthForm name="Создать"
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
                            onValidate={handleLoginValidate}
                            onChange={handleChange}/>

            </div>

            <div className={classNames(styles.field, ui.class.field)}
                 style={ui.style.field}>

                <Text value='Введите пароль'
                      className={classNames(styles.label, ui.class.passwdLabel)}
                      style={ui.style.passwdLabel}/>

                <StrengthPasswordInput name="passwd"
                                       className={ui.class.passwdInput}
                                       style={ui.style.passwdInput}
                                       onValidate={handlePasswdValidate}
                                       onChange={handleChange}/>

            </div>

            <div className={classNames(styles.field, ui.class.field)}
                 style={ui.style.field}>

                <Text value='Подтвердите пароль'
                      className={classNames(styles.label, ui.class.confirmLabel)}
                      style={ui.style.confirmLabel}/>

                <PasswordInput name="confirm"
                               className={ui.class.confirmInput}
                               style={ui.style.confirmInput}
                               onChange={handleChange}/>

            </div>

        </AuthForm>
    );

}

// -------------------------------------------------------------------------------------------------------- //

RegisterForm.propTypes = {
    to: PropTypes.string.isRequired,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            field: PropTypes.string,
            loginInput: PropTypes.string,
            loginLabel: PropTypes.string,
            passwdLabel: PropTypes.string,
            passwdInput: PropTypes.string,
            confirmLabel: PropTypes.string,
            confirmInput: PropTypes.string,
        }),
        style: PropTypes.shape({
            field: PropTypes.object,
            loginLabel: PropTypes.object,
            loginInput: PropTypes.object,
            passwdLabel: PropTypes.object,
            passwdInput: PropTypes.object,
            confirmInput: PropTypes.object,
            confirmLabel: PropTypes.object,
        }),
    }),
}
