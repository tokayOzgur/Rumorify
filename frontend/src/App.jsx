import { fetchCurrentUser } from "@/features/auth/authSlice";
import { Navbar } from "@/shared/components/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "@/app.css";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { Outlet } from "react-router-dom";
import { Footer } from "./shared/components/Footer";

function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchCurrentUser());
  }, [dispatch]);

  return (
    <div id="main-container">
      <div className="cotanier-fluid">
        <Navbar />
        <div id="content">
          <Outlet />
        </div>
        <Footer />
      </div>
    </div>
  );
}

export default App;
