import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL;

export const login = async (username, password) => {
    const response = await axios.post(`${API_URL}/auth/login`, { username, password });
    return response.data; // JWT token
};

export const register = async (username, email, password) => {
    const response = await axios.post(`${API_URL}/auth/register`, { username, email, password });
    return response.data; // Success message
};