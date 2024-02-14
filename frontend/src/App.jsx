import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; // Import Bootstrap JS
import { SignUp } from "./pages/SignUp";
import { Provider } from "react-redux";
import React from "react";
import { store } from "./redux/store";
import { Navbar } from "./shared/components/Navbar";

function App() {
  return (
    <React.StrictMode>
      <Provider store={store}>
        <div className="cotanier-fluid">
          <Navbar />
          <SignUp />
        </div>
      </Provider>
    </React.StrictMode>
  );
}

export default App;
