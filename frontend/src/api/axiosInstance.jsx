import axios from "axios";
import { i18nInstance } from "@/locales";

const instance = axios.create();

instance.interceptors.request.use((config) => {
  config.baseURL = "http://localhost:8080/api/v1";
  config.timeout = 1000;
  config.headers["Accept-Language"] = i18nInstance.language;
  config.withCredentials = true;
  return config;
});

export default instance;
