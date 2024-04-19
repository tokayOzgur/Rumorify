import React from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import notFoundImage from "@/assets/404.jpg";

export const NotFound = () => {
  const { t } = useTranslation();
  return (
    <div className="container text-center">
      <p className="mt-5 h1">{t("pageNotFoundHeader")}</p>
      <p className="lead">{t("pageNotFoundText")}</p>
      <Link to="/" className="btn btn-primary mt-3">
        {t("home")}
      </Link>
      <div className="mt-5">
        <img src={notFoundImage} alt="Not Found" className="img-fluid" />
      </div>
    </div>
  );
};
