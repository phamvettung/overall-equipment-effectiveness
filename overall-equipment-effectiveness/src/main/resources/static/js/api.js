/**
 * 
 */
// Axios setup
const api = axios.create({
  baseURL: "http://localhost:8080", // your backend
  withCredentials: true
});

// Interceptor for 401 → refresh
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        await api.post("/auth/refresh");
        return api(originalRequest);
      } catch {
        alert("Session expired, please log in again.");
        window.location.href = "/login.html";
      }
    }

    return Promise.reject(error);
  }
);