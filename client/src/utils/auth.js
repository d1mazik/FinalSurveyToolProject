// src/utils/auth.js

export const getToken = () => localStorage.getItem('token');

export const authHeader = () => {
    const token = getToken();
    if (token) {
        return {'Authorization': `Bearer ${token}`};
    } else {
        return {};
    }
};

// src/utils/auth.js

export const setToken = (token) => {
    localStorage.setItem('token', token);
};

export const setAuthData = (authData) => {
    localStorage.setItem('token', authData.token);
    localStorage.setItem('userId', authData.userId); // Antag att servern skickar userId tillsammans med token
};

export const getUserId = () => {
    return localStorage.getItem('userId');
};