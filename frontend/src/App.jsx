import { fetchCurrentUser } from "@/features/auth/authSlice";
import { Footer } from "@/shared/components/Footer";
import { Navbar } from "@/shared/components/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { Outlet } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Spinner } from "./shared/components/Spinner";

function App() {
  const dispatch = useDispatch();
  const [loading, setloading] = useState(true);

  useEffect(() => {
    dispatch(fetchCurrentUser()).finally(() => setloading(false));
  }, [dispatch]);

  if (loading) {
    return <Spinner size="lg" />;
  }

  return (
    <div className="container-fluid">
      <div className="d-flex flex-column min-vh-100">
        <Navbar />
        <Outlet />
      </div>
      <Footer />
      <ToastContainer position="top-center" theme="light" />
    </div>
  );
}

export default App;
