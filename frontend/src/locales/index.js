import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import trJson from "./translations/tr.json";
import enJson from "./translations/en.json";

const initialLanguage =
  localStorage.getItem("lang") || navigator.language || "en";

export const i18nInstance = i18n.use(initReactI18next);

i18nInstance.init({
  resources: {
    en: {
      translation: enJson,
    },
    tr: {
      translation: trJson,
    },
  },
  fallbackLng: initialLanguage,

  interpolation: {
    escapeValue: false,
  },
});
