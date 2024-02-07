import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { setName, setEmail, setPassword } from "../../redux/features/userSlice";
import { addUser } from "../../api/userApi";

export const SignUp = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);
  const [passwordRepeat, setPasswordRepeat] = useState("");
  const [apiProgress, setApiProgress] = useState(false);
  const [responMessage, setResponMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState({});
  const [generalError, setGeneralError] = useState("");

  useEffect(() => {
    setErrorMessage("");
  }, [user.username]);

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
              <h3>Sing Up</h3>
            </div>
            <label htmlFor="username">Username</label>
            <input
              className={
                errorMessage.username
                  ? "form-control mb-3 is-invalid"
                  : "form-control mb-3"
              }
              type="text"
              id="username"
              name="username"
              value={user.username}
              onChange={(e) => {
                dispatch(setName(e.target.value));
                setErrorMessage("");
              }}
            />
            <label htmlFor="password">Password:</label>
            <input
              className="form-control mb-3"
              type="password"
              name="password"
              id="password"
              value={user.password}
              onChange={(e) => {
                dispatch(setPassword(e.target.value));
              }}
            />
            <label htmlFor="passwordRepeat">Password Repeat:</label>
            <input
              className="form-control mb-3"
              type="password"
              name="passwordRepeat"
              id="passwordRepeat"
              value={passwordRepeat}
              onChange={(e) => {
                setPasswordRepeat(e.target.value);
              }}
            />
            <label htmlFor="email">Email address:</label>
            <input
              className="form-control mb-3"
              type="email"
              id="email"
              value={user.email}
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
              Sing Up
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};
