import * as actionTypes from './actionTypes';
import axios from "axios";

const USER_API_BASE_URL = 'http://localhost:8080/users';
const API_BASE_URL = 'http://localhost:8080'
export const authStart = () => {
    return {
        type: actionTypes.AUTH_START
    };
};

export const authSuccess = (token, userId) => {
    console.log(token)
    return {
        type: actionTypes.AUTH_SUCCESS,
        token: token,
        userId: userId
    };
};

export const authFail = (error) => {
    return {
        type: actionTypes.AUTH_FAIL,
        error: error
    };
};

export const logout = () => {
    return {
        type: actionTypes.AUTH_LOGOUT
    }
}

export const checkAuthTimeout = (expirationTime) => {
    return dispatch => {
        setTimeout( () => {
            dispatch(logout())
        }, expirationTime*1000)
    }
}

export const auth = (login) => {
    return dispatch => {
        dispatch(authStart());
        axios.post(USER_API_BASE_URL + '/login', login).then(response => {
            console.log(response)
            localStorage.setItem('token', response.headers.authorization)
            dispatch(authSuccess(response.headers.authorization, response.headers.userid));
            dispatch(checkAuthTimeout(response.headers.expires))
        }).catch(err => {
            console.log(err);
            dispatch(authFail(err));
        });
    };
};