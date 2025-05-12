import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/auth";

export const registerUser = (username, email, password) => {
    return axios.post(`${API_BASE_URL}/register`, {
        username,
        email,
        password,
    });
};

export const loginUser = (username, password) => {
    return axios.post(`${API_BASE_URL}/login`, {
        username,
        password,
    });
};