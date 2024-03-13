import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Input } from "@/shared/components/Input";
import { Button } from "@/shared/components/Button";
import { login } from "@/api/authApi";
import { Alert } from "@/shared/components/Alert";
import { useDispatch } from "react-redux";
import { loginSuccess } from "@/features/auth/authSlice";
import { useNavigate } from "react-router-dom";

export const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [apiProgress, setApiProgress] = useState(false);
  const [errorMessage, setErrorMessage] = useState({});
  const [generalError, setGeneralError] = useState("");
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

  const handleSubmit = (e) => {
    clearInput();
    e.preventDefault();
    setApiProgress(true);
    login({ email, password })
      .then((response) => {
        console.log("response::", response.data.user);
        dispatch(loginSuccess(response.data.user));
        navigate("/");
      })
      .catch((e) => {
        console.log("HATA::", e);
        if (e.response.data?.data) {
          if (e.response.data.status === 400) {
            setErrorMessage(e.response.data.validationError);
          } else {
            setGeneralError(e.response.data.message);
          }
        } else {
          setGeneralError(e.response.data.message);
        }
      })
      .finally(() => {
        setApiProgress(false);
      });
  };

  const clearInput = () => {
    setEmail("");
    setPassword("");
    setApiProgress(false);
    setGeneralError("");
  };
  return (
    <div className="container my-5">
      <div className="row">
        <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
          <form className="card p-3" onSubmit={handleSubmit}>
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

            {generalError && <Alert styleType="danger">{generalError}</Alert>}

            <Button
              disabled={!email || !password || apiProgress}
              apiProgress={apiProgress}
              btnClass={"btn btn-primary"}
              children={t("login")}
            />
          </form>
        </div>
      </div>
    </div>
  );
};
