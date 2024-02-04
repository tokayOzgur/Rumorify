import { useDispatch, useSelector } from "react-redux";
import { useState } from "react";
import { setName, setEmail, setPassword } from "../../redux/features/userSlice";
import { addUser } from "../../api/userApi";

export const SignUp = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);
  const [passwordRepeat, setPasswordRepeat] = useState("");
  const [apiProgress, setApiProgress] = useState(false);
  const [responMessage, setResponMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (user.password === passwordRepeat) {
      setResponMessage("");
      console.log("handleSubmit" + user);
      setApiProgress(true);
      addUser(user)
        .catch((e) => {
          console.log("HATA::", e);
        })
        .then((response) => {
          setResponMessage(response.data.message);
        })
        .finally(() => {
          clearInput();
        });
    }
  };

  const clearInput = () => {
    dispatch(setName(""));
    dispatch(setEmail(""));
    dispatch(setPassword(""));
    setPasswordRepeat("");
    setApiProgress(false);
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
              className="form-control mb-3"
              type="text"
              id="username"
              name="username"
              value={user.username}
              onChange={(e) => {
                dispatch(setName(e.target.value));
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
