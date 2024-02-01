import { useDispatch, useSelector } from "react-redux";
import { setName, setEmail, setPassword } from "../../redux/features/userSlice";
import { addUser } from "../../api/userApi";
import { useState } from "react";

export const SignUp = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);
  const [passwordRepeat, setPasswordRepeat] = useState("");

  const handleSubmit = (e) => {
    e.prevenDefault();
    if (user.password === passwordRepeat) {
      console.log("handleSubmit" + user.isim);
      addUser(user).catch((e) => {
        console.log("HATA::", e);
      });
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="container my-5">
        <div className="row">
          <div className="col-6">
            <label htmlFor="username">Username</label>
          </div>
          <div className="col-6">
            <input
              type="text"
              id="username"
              name="username"
              value={user.username}
              onChange={(e) => {
                dispatch(setName(e.target.value));
              }}
            />
          </div>
          <div className="col-6">
            <label htmlFor="password">Password:</label>
          </div>
          <div className="col-6">
            <input
              type="password"
              name="password"
              id="password"
              value={user.password}
              onChange={(e) => {
                dispatch(setPassword(e.target.value));
              }}
            />
          </div>
          <div className="col-6">
            <label htmlFor="passwordRepeat">Password Repeat:</label>
          </div>
          <div className="col-6">
            <input
              type="password"
              name="passwordRepeat"
              id="passwordRepeat"
              value={user.passwordRepeat}
              onChange={(e) => {
                setPasswordRepeat(e.target.value);
              }}
            />
          </div>
          <div className="col-6">
            <label htmlFor="email">Email address:</label>
          </div>
          <div className="col-6">
            <input
              type="text"
              id="email"
              value={user.email}
              onChange={(e) => {
                dispatch(setEmail(e.target.value));
              }}
            />
          </div>
          <button
            className="btn btn-primary"
            disabled={user.password !== passwordRepeat}
          >
            Sing Up
          </button>
        </div>
      </div>
    </form>
  );
};
