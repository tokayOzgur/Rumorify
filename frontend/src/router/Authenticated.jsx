import { useSelector } from "react-redux";
import { Navigate, useLocation } from "react-router-dom";

export const Authenticated = ({ children }) => {
  let location = useLocation();
  const { id } = useSelector((store) => store.auth);
  if ((id > 0)) {
    return <Navigate to="/home" state={{ from: location }} replace />;
  }
  return children;
};
