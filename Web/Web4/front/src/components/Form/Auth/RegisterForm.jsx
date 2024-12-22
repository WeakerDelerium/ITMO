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
import {StrengthPasswordInput} from "../../Input/Auth/StrengthPasswordInput.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {registerFormDataPattern, formDataChange, status, toCredentialsDTO} from "../../../utils/form.js";
import {axiosRegister} from "../../../utils/axios.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "../Form.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function RegisterForm({to, ui = uiDefault, ...rest}) {

    const dispatch = useDispatch();
    const state = useSelector(st => st.state);
    const handleStateReset = () => dispatch(resetState());

    const [formData, setFormData] = useState(registerFormDataPattern);

    function handleChange(event) {
        if (state) handleStateReset();
        formDataChange(event, formData, setFormData);
    }

    const [loginValid, setLoginValid] = useState(false);
    const [passwordValid, setPasswordValid] = useState(false);

    const handleValidate = (valid, setter) => setter(() => valid);

    const handleLoginValidate = valid => handleValidate(valid, setLoginValid);
    const handlePasswordValidate = valid => handleValidate(valid, setPasswordValid);

    async function handleSubmit() {
        const username = formData.username;
        const password = formData.password;
        const confirm = formData.confirm;

        return (!username) ? status.EMPTY_USERNAME :
               (!password) ? status.EMPTY_PASSWORD :
               (!loginValid) ? status.INVALID_USERNAME :
               (!passwordValid) ? status.INVALID_PASSWORD :
               (password !== confirm) ? status.MISMATCH_PASSWORDS :
               axiosRegister(toCredentialsDTO(formData), status.OK, status.USER_EXISTS, status.UNEXPECTED);
    }

    return (
        <AuthForm name="Создать"
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
                            onValidate={handleLoginValidate}
                            onChange={handleChange}/>

            </div>

            <div className={classNames(styles.field, ui.class.field)}
                 style={ui.style.field}>

                <Text value='Введите пароль'
                      className={classNames(styles.label, ui.class.passwordLabel)}
                      style={ui.style.passwordLabel}/>

                <StrengthPasswordInput name="password"
                                       className={ui.class.passwordInput}
                                       style={ui.style.passwordInput}
                                       onValidate={handlePasswordValidate}
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
            passwordLabel: PropTypes.string,
            passwordInput: PropTypes.string,
            confirmLabel: PropTypes.string,
            confirmInput: PropTypes.string,
        }),
        style: PropTypes.shape({
            field: PropTypes.object,
            loginLabel: PropTypes.object,
            loginInput: PropTypes.object,
            passwordLabel: PropTypes.object,
            passwordInput: PropTypes.object,
            confirmInput: PropTypes.object,
            confirmLabel: PropTypes.object,
        }),
    }),
}
