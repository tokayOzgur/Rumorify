import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import React from "react";
import { Provider } from "react-redux";
import "./App.css";
import { store } from "./app/store";
import { Navbar } from "./shared/components/Navbar";
import { Outlet } from "react-router-dom";

function App() {
  return (
    <Provider store={store}>
      <div className="cotanier-fluid">
        <Navbar />
        <Outlet />
      </div>
    </Provider>
  );
}

export default App;
