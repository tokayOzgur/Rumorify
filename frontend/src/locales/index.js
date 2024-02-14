import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import trJson from "./translations/tr.json";
import enJson from "./translations/en.json";

i18n.use(initReactI18next).init({
  resources: {
    en: {
      translations: enJson,
    },
    tr: {
      translations: trJson,
    },
  },
  fallbackLng: "en",
  interpolation: {
    escapeValue: false,
  },
});
