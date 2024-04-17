import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Input } from "@/shared/components/Input";
import { Button } from "@/shared/components/Button";
import { login } from "@/api/authApi";
import { Alert } from "@/shared/components/Alert";
import { useDispatch } from "react-redux";
import { loginSuccess } from "@/features/auth/authSlice";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

export const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [apiProgress, setApiProgress] = useState(false);
  const [errorMessage, setErrorMessage] = useState({});
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    setErrorMessage((lastErrors) => {
      return {
        ...lastErrors,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(() => {
    setErrorMessage((lastErrors) => {
      return {
        ...lastErrors,
        password: undefined,
      };
    });
  }, [password]);

  const handleSubmit = async (e) => {
    clearInput();
    e.preventDefault();
    setApiProgress(true);
    try {
      const response = await login({ email, password });
      dispatch(loginSuccess(response.data));
      navigate("/");
    } catch (err) {
      if (err.response.data?.data) {
        if (err.response.data.status === 400) {
          setErrorMessage(err.response.data.validationError);
        } else {
          toast.error(err.response.data.message);
        }
      } else {
        toast.error(err.response.data.message);
      }
    } finally {
      setApiProgress(false);
    }
  };

  const clearInput = () => {
    setEmail("");
    setPassword("");
    setApiProgress(false);
  };
  return (
    <div className="container my-5">
      <div className="row">
        <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
          <div className="card p-3">
            <form onSubmit={handleSubmit}>
              <div className="card-header mb-3">
                <h3>{t("login")}</h3>
              </div>
              <Input
                id={"email"}
                type={"email"}
                label={t("email")}
                error={errorMessage.email}
                onChange={(e) => {
                  setEmail(e.target.value);
                }}
              />
              <Input
                id={"password"}
                type={"password"}
                label={t("password")}
                error={errorMessage.password}
                onChange={(e) => {
                  setPassword(e.target.value);
                }}
              />

              <div className="col-12">
                <Button
                  disabled={!email || !password || apiProgress}
                  apiProgress={apiProgress}
                  btnClass={"btn btn-primary w-100"}
                  children={t("login")}
                />
              </div>
            </form>
            <div className="col-12 text-center mt-3">
              <Link
                to="/signup"
                className="font-monospace text-opacity-75 text-primary text-decoration-none"
              >
                <span className="mx-2">{t("noAccount")}</span>
                <span>{t("signUp")}</span>
              </Link>
            </div>
            <div className="col-12 text-center ">
              <Link
                to={"/password-reset"}
                className="font-monospace text-opacity-75 text-primary text-decoration-none"
              >
                <span className="mx-2">{t("forgotPassword")}</span>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
