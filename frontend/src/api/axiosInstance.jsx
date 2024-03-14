import axios from "axios";
import { i18nInstance } from "@/locales";
import { loadToken, storeToken } from "@/shared/state/storage";

let authToken = loadToken();
export const setToken = (token) => {
  authToken = token;
  storeToken(token);
};

const instance = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  timeout: 1000,
  headers: {
    "X-Custom-Header": "foobar",
    "Accept-Language": i18nInstance.language,
    "Content-Type": "application/json",
  },
});

instance.interceptors.request.use((config) => {
  if (authToken) {
    config.headers["Authorization"] = `${authToken.prefix} ${authToken.token}`;
  }
  return config;
});

export default instance;
