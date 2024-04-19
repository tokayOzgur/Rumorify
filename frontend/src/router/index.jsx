import App from "@/App";
import { Activation } from "@/pages/Activation";
import { Home } from "@/pages/Home";
import { Introduction } from "@/pages/Introduction";
import { Login } from "@/pages/Login";
import { NotFound } from "@/pages/NotFound";
import { NewPassword } from "@/pages/PasswordReset/NewPassword";
import { PasswordResetRequest } from "@/pages/PasswordReset/PasswordResetRequest";
import { SignUp } from "@/pages/SignUp";
import { User } from "@/pages/User";
import { createBrowserRouter } from "react-router-dom";
import { Authenticated } from "./Authenticated.jsx";
import { RequireAuth } from "./RequireAuth.jsx";

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
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
]);
