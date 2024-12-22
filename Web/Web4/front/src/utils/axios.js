import axios from 'axios';
import Cookies from 'js-cookie';
import {StatusCodes} from "http-status-codes";

// -------------------------------------------------------------------------------------------------------- //

axios.defaults.baseURL = 'http://localhost:5252/api';

// -------------------------------------------------------------------------------------------------------- //

const tokenConfig = {
    // httpOnly: true,
    expires: 7,
    sameSite: 'Strict',
}

// -------------------------------------------------------------------------------------------------------- //

const setToken = (name, token) => Cookies.set(name, token, tokenConfig);
const getToken = name => Cookies.get(name);
const removeToken = name => Cookies.remove(name);

// -------------------------------------------------------------------------------------------------------- //

const getTokenDTO = name => ({token: getToken(name)});

// -------------------------------------------------------------------------------------------------------- //

function errorRedirect(error) {
    window.location.href = '/error';
    return Promise.reject(error);
}

function severUnavailableRedirect(error) {
    if (error.response?.status === StatusCodes.INTERNAL_SERVER_ERROR) return errorRedirect(error);
    return Promise.reject(error);
}

// -------------------------------------------------------------------------------------------------------- //

const tokens = ['access', 'refresh']

function storeTokens(response) {
    const data = response.data;
    tokens.forEach(token => setToken(token, data[token]));
    return response;
}

const deleteTokens = () => tokens.forEach(token => removeToken(token));

// -------------------------------------------------------------------------------------------------------- //

function buildAuthHeader(request) {
    if (!authenticated()) return axiosLogout();
    const access = getToken('access');
    if (access) request.headers.Authorization = `Bearer ${access}`;
    return request;
}

// -------------------------------------------------------------------------------------------------------- //

function updateAccessToken() {
    return axios
        .post('/auth/tokens', getTokenDTO('refresh'))
        .then(resp => setToken('access', resp.data.token));
}

async function handleTokenError(error) {
    const config = error.config;

    const response = error.response;
    if (!response || response.status !== StatusCodes.FORBIDDEN || config._retry) return errorRedirect(error);
    config._retry = true;

    try {
        await updateAccessToken();
        return APIFetcher(config);
    } catch (err) {
        const unauthorized = [StatusCodes.FORBIDDEN, StatusCodes.BAD_REQUEST].includes(err.response?.status);
        if (unauthorized) deleteTokens();
        window.location.href = unauthorized ? '/' : '/error';
        return Promise.reject(err);
    }
}

// -------------------------------------------------------------------------------------------------------- //

export const authFetcher = axios.create();
authFetcher.interceptors.response.use(storeTokens, severUnavailableRedirect);

// -------------------------------------------------------------------------------------------------------- //

function axiosAuthorization(to, dto, ok, cond, thn, els) {
    return authFetcher
        .post(to, dto)
        .then(() => ok)
        .catch(error => cond(error) ? thn : els);
}

const axiosLoginCondition = e => [StatusCodes.NOT_FOUND, StatusCodes.UNAUTHORIZED].includes(e.response?.status);
const axiosRegisterCondition = e => e.response?.status === StatusCodes.CONFLICT;

export const axiosLogin = (dto, ok, thn, els) => axiosAuthorization("/auth/login", dto, ok, axiosLoginCondition, thn, els);
export const axiosRegister = (dto, ok, thn, els) => axiosAuthorization("/auth/register", dto, ok, axiosRegisterCondition, thn, els);

// -------------------------------------------------------------------------------------------------------- //

export function axiosLogout() {
    authFetcher
        .post('/auth/logout', getTokenDTO('refresh'))
        .finally(deleteTokens);
}

// -------------------------------------------------------------------------------------------------------- //

export const authenticated = () => Boolean(getToken('refresh'));

// -------------------------------------------------------------------------------------------------------- //

export const APIFetcher = axios.create();
APIFetcher.interceptors.request.use(buildAuthHeader, errorRedirect);
APIFetcher.interceptors.response.use(resp => resp, handleTokenError);

// -------------------------------------------------------------------------------------------------------- //

export function axiosGetPoints(handler) {
    APIFetcher
        .get('/point/all')
        .then(resp => handler(resp.data));
}

export function axiosAddPoint(dto, handler) {
    APIFetcher
        .post('/point/check', dto)
        .then(resp => handler(resp.data))
        .catch(error => console.log(error));
}
