import { createBrowserRouter } from "react-router-dom";
import { Home } from "@/pages/Home";
import { SignUp } from "@/pages/SignUp";
import App from "@/App";
import { Activation } from "@/pages/Activation";
import { User } from "@/pages/User";
import { Login } from "@/pages/Login";
import { Introduction } from "@/pages/Introduction";
import { RequireAuth } from "./RequireAuth.jsx";
import { Authenticated } from "./Authenticated.jsx";
import { PasswordResetRequest } from "@/pages/PasswordReset/PasswordResetRequest";
import { NewPassword } from "@/pages/PasswordReset/NewPassword";

export default createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        index: true,
        element: (
          <Authenticated>
            <Introduction />
          </Authenticated>
        ),
      },
      {
        path: "/home",
        element: (
          <RequireAuth>
            <Home />
          </RequireAuth>
        ),
      },
      {
        path: "/signup",
        element: <SignUp />,
      },
      {
        path: "/activation/:token",
        element: (
          <Authenticated>
            <Activation />
          </Authenticated>
        ),
      },
      {
        path: "/update-password/:token",
        element: <NewPassword />,
      },
      {
        path: "/password-reset",
        element: <PasswordResetRequest />,
      },
      {
        path: "/user/:id",
        element: (
          <RequireAuth>
            <User />
          </RequireAuth>
        ),
      },
      {
        path: "/login",
        element: (
          <Authenticated>
            <Login />
          </Authenticated>
        ),
      },
    ],
  },
]);
