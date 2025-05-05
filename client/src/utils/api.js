// src/utils/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/auth/api',
  headers: { 'Content-Type': 'application/json' },
});

// ✅ Add interceptor to attach token to every request
api.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // <-- Make sure "Bearer" is included
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
