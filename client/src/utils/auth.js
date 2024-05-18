// src/utils/auth.js

// Functions to handle token and userId storage in local storage.

export const setToken = (token, userId) => {
    localStorage.setItem('token', token);
    localStorage.setItem('userId', userId); // Storing userId
};

export const getToken = () => {
    return localStorage.getItem('token');
};

export const getUserId = () => {
    return localStorage.getItem('userId');
};

export const removeAuthData = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
};

export const authHeader = () => {
    const token = getToken();
    if (token) {
        return { 'Authorization': `Bearer ${token}` };
    } else {
        return {};
    }
};


