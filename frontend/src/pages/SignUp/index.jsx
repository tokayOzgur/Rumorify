import { useState } from "react";

export const SignUp = () => {
  const [user, setUser] = useState({
    username: "",
    password: "",
    passwordRepeat: "",
    email: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  return (
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
            onChange={handleInputChange}
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
            onChange={handleInputChange}
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
            onChange={handleInputChange}
          />
        </div>
        <div className="col-6">
          <label htmlFor="email">Email address:</label>
        </div>
        <div className="col-6">
          <input
            type="email"
            id="email"
            aria-describedby="emailHelp"
            value={user.email}
            onChange={handleInputChange}
          />
        </div>
        <button
          className="btn btn-primary"
          disabled={user.password !== user.passwordRepeat}
        >
          Sing Up
        </button>
      </div>
    </div>
  );
};
