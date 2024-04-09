import { fetchCurrentUser } from "@/features/auth/authSlice";
import { Navbar } from "@/shared/components/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { Outlet } from "react-router-dom";

function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCurrentUser());
  }, [dispatch]);

  return (
    <div className="cotanier-fluid">
      <Navbar />
      <Outlet />
    </div>
  );
}

export default App;
