import { useSelector } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

export const RequireAuth = ({ children }) => {
  let location = useLocation();
  const { id } = useSelector((store) => store.auth);
  if (id == 0) {
    return <Navigate to="/" state={{ from: location }} replace />;
  }
  return children;
};
