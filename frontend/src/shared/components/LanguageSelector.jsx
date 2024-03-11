import { useTranslation } from "react-i18next";

export const LanguageSelector = () => {
  const { t, i18n } = useTranslation();

  const onSelectLanguage = (language) => {
    i18n.changeLanguage(language);
    localStorage.setItem("lang", language);
  };

  return (
    <div className="row">
      <li>
        <button
          className="btn btn-light dropdown-item"
          onClick={() => onSelectLanguage("en")}
        >
          {t("english")}
        </button>
      </li>
      <li>
        <button
          className="btn btn-light dropdown-item"
          onClick={() => onSelectLanguage("tr")}
        >
          {t("turkish")}
        </button>
      </li>
    </div>
  );
};
