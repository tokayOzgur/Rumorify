import { addUser } from "@/api/userApi";
import { Alert } from "@/shared/components/Alert";
import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/Input";
import { useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";

//TODO: test validation error and susccess message
export const SignUp = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeat, setPasswordRepeat] = useState("");
  const [apiProgress, setApiProgress] = useState(false);
  const [responMessage, setResponMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState({});
  const [generalError, setGeneralError] = useState("");
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

  const handleSubmit = (e) => {
    clearInput();
    e.preventDefault();
    if (password === passwordRepeat) {
      setResponMessage("");
      setApiProgress(true);
      addUser({ username, email, password })
        .catch((e) => {
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
        .then((response) => {
          setResponMessage(response.data.message);
        })
        .finally(() => {
          setApiProgress(false);
        });
    }
  };

  const clearInput = () => {
    setUsername("");
    setEmail("");
    setPassword("");
    setPasswordRepeat("");
    setApiProgress(false);
    setResponMessage("");
    setGeneralError("");
  };

  return (
    <div className="container my-5">
      <div className="row">
        <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
          <form className="card p-3" onSubmit={handleSubmit}>
            <div className="card-header mb-3">
              <h3>{t("signUp")}</h3>
            </div>
            <Input
              id={"username"}
              type={"text"}
              label={t("username")}
              error={errorMessage.username}
              onChange={(e) => {
                dispatch(setName(e.target.value));
              }}
            />
            <Input
              id={"password"}
              type={"password"}
              label={t("password")}
              error={errorMessage.password}
              onChange={(e) => {
                dispatch(setPassword(e.target.value));
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
                dispatch(setEmail(e.target.value));
              }}
            />

            {responMessage && (
              <Alert styleType="success">{responMessage}</Alert>
            )}
            {generalError && (
              <Alert styleType="success-danger">{generalError}</Alert>
            )}

            <Button
              children={t("signUp")}
              disabled={!password || password !== passwordRepeat}
              apiProgress={apiProgress}
              btnClass={"btn btn-primary"}
            />
          </form>
        </div>
      </div>
    </div>
  );
};
