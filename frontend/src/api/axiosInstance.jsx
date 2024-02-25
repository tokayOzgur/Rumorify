import axios from "axios";
import { i18nInstance } from "@/locales";

const instance = axios.create({
  baseURL: "http://localhost:8080/api/v1/users",
  timeout: 1000,
  headers: {
    "X-Custom-Header": "foobar",
    "Accept-Language": i18nInstance.language,
  },
});

export default instance;
