import { useTranslation } from "react-i18next";

export const LanguageSelector = () => {
  const { t, i18n } = useTranslation();
  return (
    <div className="row">
      <li>
        <button
          className="btn btn-light dropdown-item"
          onClick={() => i18n.changeLanguage("en")}
        >
          {t("english")}
        </button>
      </li>
      <li>
        <button
          className="btn btn-light dropdown-item"
          onClick={() => i18n.changeLanguage("tr")}
        >
          {t("turkish")}
        </button>
      </li>
    </div>
  );
};
