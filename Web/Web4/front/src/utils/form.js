export const loginFormDataPattern = {username: "", password: ""};
export const registerFormDataPattern = {username: "", password: "", confirm: ""};
export const pointFordDataPattern = {X: "", Y: "", R: ""};

// -------------------------------------------------------------------------------------------------------- //

export const toCredentialsDTO = formData => ({username: formData.username, password: formData.password});
export const toPointDTO = formData => ({x: formData.X, y: formData.Y, r: formData.R});

// -------------------------------------------------------------------------------------------------------- //

export const formDataChange = (event, data, setter) => setter({...data, [event.target.name]: event.target.value});

// -------------------------------------------------------------------------------------------------------- //

export const numberPattern = /^-?\d*[.,]?\d*$/;
export const loginPattern = /^[a-zA-Z\d]{0,24}$/;
export const passwordPattern = /^[a-zA-Z\d~`!@#$%^&*()_\-+={}[\]|\\:;"'<,>.?/]{0,24}$/;

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

export const validateLogin = val => val.length >= 3;
export const validatePassword = val => countStrengthScore(val) >= 5;

export const validatePoint = fd => !Object.values(fd).some(val => !val);
export const validateX = val => -3 <= val && val <= 5;
export const validateY = val => -3 <= val && val <= 5;

// -------------------------------------------------------------------------------------------------------- //

export const status = Object.freeze({
    OK: null,
    EMPTY_USERNAME: "Введите имя",
    EMPTY_PASSWORD: "Введите пароль",
    EMPTY_FIELDS: "Не все поля выбраны",
    EMPTY_R: "Выберите радиус",
    INVALID_USERNAME: "Слишком короткое имя",
    INVALID_PASSWORD: "Пароль слишком простой",
    INVALID_X: "X ∉ D(f)",
    INVALID_Y: "Y ∉ D(f)",
    MISMATCH_PASSWORDS: "Пароли не совпадают",
    AUTHENTICATION: "Неверное имя или пароль",
    USER_EXISTS: "Пользователь существует",
    AREA_NOT_DEFINED: "Область не определена",
    UNEXPECTED: "Неожиданная ошибка на сервере"
});
