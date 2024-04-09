import { logout } from "@/api/authApi";
import logo from "@/assets/a.png";
import { logoutSuccess } from "@/features/auth/authSlice";
import { useTranslation } from "react-i18next";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { LanguageSelector } from "./LanguageSelector";
import { ProfileImage } from "./ProfileImage";
import { useNavigate } from "react-router-dom";

export const Navbar = () => {
  const { t } = useTranslation();
  const authState = useSelector((store) => store.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const onClickLogout = async () => {
    try {
      await logout();
      navigate("/")
    } catch (err) {
      //TODO: handle error
      console.log(err);
    } finally {
      dispatch(logoutSuccess());
    }
  };

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
            <div className="d-flex">
              {authState.id === 0 && (
                <>
                  <Link className="nav-link mx-3" to="/login">
                    {t("login")}
                  </Link>
                  <Link className="nav-link" to="/signup">
                    {t("signUp")}
                  </Link>
                </>
              )}
              {authState.id > 0 && (
                <>
                  <Link className="nav-link mx-3" to={`/user/${authState.id}`}>
                    <ProfileImage
                      alt={"profile-image"}
                      src={authState.image}
                      width={30}
                      height={30}
                      className={"rounded-circle"}
                    />
                  </Link>
                  <span
                    className="nav-link"
                    role="button"
                    onClick={onClickLogout}
                  >
                    {t("logout")}
                  </span>
                </>
              )}
            </div>
          </div>
        </div>
      </nav>
    </div>
  );
};
