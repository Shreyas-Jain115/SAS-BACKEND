// api/axiosInstance.js
import axios from 'axios';
import Cookies from 'js-cookie';
const BASE_URL = 'http://your-backend-url.com/api'; // adjust this

const axiosInstance = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

// Define paths that do NOT require token
const excludeAuthPaths = [
  '/user/login/basic',
  '/user/login/otp',
  '/user/admin/signUp/basic',
  '/user/admin/signUp/otp',
  '/user/reset/basic',
  '/user/reset/otp'
];

axiosInstance.interceptors.request.use(
  (config) => {
    const shouldExclude = excludeAuthPaths.some((path) =>
      config.url.includes(path)
    );

    if (!shouldExclude) {
      const token = Cookies.get('bearerToken') || null // Or use cookies, session, etc.
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
      }
    }

    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosInstance;
