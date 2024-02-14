import { useDispatch, useSelector } from "react-redux";
import { useEffect, useMemo, useState } from "react";
import { setName, setEmail, setPassword } from "../../redux/features/userSlice";
import { addUser } from "../../api/userApi";
import { Input } from "../../components/SignUp/Input";
import { useTranslation } from "react-i18next";

//TODO: test validation error and susccess message
export const SignUp = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);
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
        user: {
          ...lastErrors.user,
          username: undefined,
        },
      };
    });
  }, [user.username]);

  useEffect(() => {
    setErrorMessage((lastErrors) => {
      return {
        ...lastErrors,
        user: {
          ...lastErrors.user,
          email: undefined,
        },
      };
    });
  }, [user.email]);

  useEffect(() => {
    setErrorMessage((lastErrors) => {
      return {
        ...lastErrors,
        user: {
          ...lastErrors.user,
          password: undefined,
        },
      };
    });
  }, [user.password]);

  const passwordRepeatError = useMemo(() => {
    if (user.password !== passwordRepeat) {
      return this.t("passwordMismatch");
    }
    return undefined;
  }, [user.password, passwordRepeat]);

  const handleSubmit = async (e) => {
    clearInput();
    e.preventDefault();
    if (user.password === passwordRepeat) {
      setResponMessage("");
      setApiProgress(true);
      await addUser(user)
        .catch((e) => {
          console.log("HATA::", e);
          if (e.response.data?.data && e.response.data.status === 400) {
            setErrorMessage(e.response.data.validationError);
          } else {
            setGeneralError(e.response.data.message);
          }
        })
        .then((response) => {
          setResponMessage(response.data.message);
        })
        .finally(() => {
          apiProgress(false);
        });
    }
  };

  const clearInput = () => {
    dispatch(setName(""));
    dispatch(setEmail(""));
    dispatch(setPassword(""));
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
              <div className="alert alert-success">{responMessage}</div>
            )}
            {generalError && (
              <div className="alert alert-danger">{generalError}</div>
            )}

            <button
              className="btn btn-primary"
              disabled={
                !user.password ||
                user.password !== passwordRepeat ||
                apiProgress
              }
            >
              {apiProgress && (
                <span
                  className="mx-2 spinner-border spinner-border-sm"
                  aria-hidden="true"
                ></span>
              )}
              {t("signUp")}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};
