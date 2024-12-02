export const loginFormDataPattern = {login: "", passwd: ""};
export const registerFormDataPattern = {login: "", passwd: "", confirm: ""};
export const pointFordDataPattern = {X: "", Y: "", R: ""};

// -------------------------------------------------------------------------------------------------------- //

export const formDataChange = (event, data, setter) => setter({...data, [event.target.name]: event.target.value});

// -------------------------------------------------------------------------------------------------------- //

export const numberPattern = /^-?\d*[.,]?\d*$/;
export const loginPattern = /^[a-zA-Z\d]{0,24}$/;
export const passwdPattern = /^[a-zA-Z\d~`!@#$%^&*()_\-+={}[\]|\\:;"'<,>.?/]{0,24}$/;

// -------------------------------------------------------------------------------------------------------- //

export function sliceOutPattern(event, pattern) {
    const input = event.target;

    let val = input.value;
    const last = input.selectionStart - 1;

    if (!pattern.test(val))
        val = val.slice(0, last) + val.slice(last + 1);

    return val;
}

// -------------------------------------------------------------------------------------------------------- //

export function countStrengthScore(value) {
    return (value.length >= 8) + (value.length >= 16) + /[a-z]/.test(value) + /[A-Z]/.test(value) +
        /\d/.test(value) + /[~`!@#$%^&*()_\-+={[}\]|\\:;"'<,>.?/]/.test(value);
}

// -------------------------------------------------------------------------------------------------------- //

export const getPointFormData = (X, Y, R) => ({X, Y, R});

// -------------------------------------------------------------------------------------------------------- //

export function validateLogin(value) {
    return (value.length >= 3);
}

export function validatePasswd(value) {
    return countStrengthScore(value) >= 5;
}

export function validatePoint(formData) {
    return !Object
        .values(formData)
        .some(val => !val);
}

// -------------------------------------------------------------------------------------------------------- //

export const status = Object.freeze({
    OK: null,
    EMPTY_LOGIN: "Введите логин",
    EMPTY_PASSWORD: "Введите пароль",
    EMPTY_FIELDS: "Не все поля выбраны",
    EMPTY_R: "Выберите радиус",
    INVALID_LOGIN: "Слишком короткий логин",
    INVALID_PASSWORD: "Пароль слишком простой",
    MISMATCH_PASSWORDS: "Пароли не совпадают",
    WRONG_LOGIN_OR_PASSWORD: "Неверный логин или пароль",
    USER_EXISTS: "Пользователь существует",
});
