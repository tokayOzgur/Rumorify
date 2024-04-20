import { addUser } from "@/api/userApi";
import { Alert } from "@/shared/components/Alert";
import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/Input";
import { useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";

//TODO: test validation error and susccess message
export const SignUp = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeat, setPasswordRepeat] = useState("");
  const [apiProgress, setApiProgress] = useState(false);
  const [errorMessage, setErrorMessage] = useState({});
  const { t } = useTranslation();

  useEffect(() => {
    setErrorMessage((lastErrors) => {
      return {
        ...lastErrors,
        username: undefined,
      };
    });
  }, [username]);

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

  const passwordRepeatError = useMemo(() => {
    if (password !== passwordRepeat) {
      return t("passwordMismatch");
    }
    return undefined;
  }, [password, passwordRepeat, t]);

  const handleSubmit = async (e) => {
    clearInput();
    e.preventDefault();
    if (password === passwordRepeat) {
      setApiProgress(true);
      try {
        const response = await addUser({ username, email, password });
        toast.success(response.data.message);
      } catch (err) {
        console.log(err);
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
    }
  };

  const clearInput = () => {
    setUsername("");
    setEmail("");
    setPassword("");
    setPasswordRepeat("");
    setApiProgress(false);
    setErrorMessage({});
  };

  return (
    <div className="container my-5">
      <div className="row">
        <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
          <div className="card p-3">
            <form onSubmit={handleSubmit}>
              <div className="card-header mb-3">
                <h3>{t("signUp")}</h3>
              </div>
              <Input
                id={"username"}
                type={"text"}
                label={t("username")}
                error={errorMessage.username}
                onChange={(e) => {
                  setUsername(e.target.value);
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
              <Input
                id={"passwordRepeat"}
                type={"password"}
                label={t("passwordRepeat")}
                error={passwordRepeatError}
                onChange={(e) => {
                  setPasswordRepeat(e.target.value);
                }}
              />
              <Input
                id={"email"}
                type={"email"}
                label={t("email")}
                error={errorMessage.email}
                onChange={(e) => {
                  setEmail(e.target.value);
                }}
              />
              <Button
                children={t("signUp")}
                disabled={!password || password !== passwordRepeat}
                apiProgress={apiProgress}
                btnClass={"btn btn-primary w-100"}
              />
            </form>
            <div className="col-12 text-center mt-3">
              <Link
                to="/login"
                className="font-monospace text-opacity-75 text-primary text-decoration-none"
              >
                <span className="mx-2">{t("alreadyHaveAccount")}</span>
                <span>{t("login")}</span>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
