import React from "react";
import { useTranslation } from "react-i18next";
import rumorifyLogo from "@/assets/a.png";
import { Link, useNavigate } from "react-router-dom";

export const Footer = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  return (
    <footer className="bg-light text-center text-lg-start rounded mt-5">
      <div className="container p-4">
        <div className="row">
          <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
            <img src={rumorifyLogo} alt="Rumorify Logo" className="mb-2 w-25" />
            <p className="text-muted">{t("footerText")}</p>
          </div>

          <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
            <h5 className="text-uppercase mb-4">{t("links")}</h5>
            <ul className="list-unstyled">
              <li>
                <Link to="/" className="text-dark">
                  {t("home")}
                </Link>
              </li>
              <li>
                <Link to="/login" className="text-dark">
                  {t("login")}
                </Link>
              </li>
              <li>
                <Link to="/signup" className="text-dark">
                  {t("signup")}
                </Link>
              </li>
            </ul>
          </div>

          <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
            <h5 className="text-uppercase mb-4">{t("socialMedia")}</h5>
            <ul className="list-unstyled">
              <li>
                <a href="#" className="me-2 text-dark">
                  <i className="fab fa-facebook-f"></i> Facebook
                </a>
              </li>
              <li>
                <a href="#" className="me-2 text-dark">
                  <i className="fab fa-twitter"></i> Twitter
                </a>
              </li>
              <li>
                <a href="#" className="me-2 text-dark">
                  <i className="fab fa-instagram"></i> Instagram
                </a>
              </li>
              <li>
                <a href="#" className="me-2 text-dark">
                  <i className="fab fa-linkedin-in"></i> LinkedIn
                </a>
              </li>
            </ul>
          </div>

          <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
            <h5 className="text-uppercase mb-4">{t("subscribe")}</h5>
            <form>
              <div className="form-outline form-white mb-4">
                <input
                  type="email"
                  id="form5Example2"
                  className="form-control"
                  placeholder="Email adresiniz"
                />
                <button type="submit" className="btn btn-outline-dark mt-4">
                  {t("subscribe")}
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div
        className="text-center p-3"
        style={{ backgroundColor: "rgba(0, 0, 0, 0.2)" }}
      >
        {t("allRightsReserved")}
        <a href="/privacy" className="text-dark">
          {t("privacyPolicy")}
        </a>
        |
        <a href="/terms" className="text-dark">
          {t("termsAndConditions")}
        </a>
      </div>
    </footer>
  );
};
