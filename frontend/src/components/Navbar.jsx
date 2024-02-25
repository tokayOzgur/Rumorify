import { LanguageSelector } from "./LanguageSelector";
import logo from "../assets/a.png";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

export const Navbar = () => {
  const { t } = useTranslation();

  return (
    <div className="m-2 shadow-sm">
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand" href="/">
            <img
              src={logo}
              alt="rumorify_logo"
              width={60}
              className="rounded mx-2"
            />
            Rumorify
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item dropdown">
                <a
                  className="nav-link dropdown-toggle"
                  href="#"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  {t("preferences")}
                </a>
                <ul className="dropdown-menu">
                  <li>
                    <a className="dropdown-item" href="#">
                      Action
                    </a>
                  </li>
                  <li>
                    <hr className="dropdown-divider" />
                  </li>
                  <LanguageSelector />
                </ul>
              </li>
            </ul>
            <div className="d-flex mx-2">
              <Link className="nav-link" to="/signup">
                {t("signUp")}
              </Link>
            </div>
          </div>
        </div>
      </nav>
    </div>
  );
};