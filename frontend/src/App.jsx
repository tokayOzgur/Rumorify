import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { SignUp } from "./pages/SignUp";
import { Provider } from "react-redux";
import React from "react";
import { store } from "./redux/store";

function App() {
  return (
    <React.StrictMode>
      <Provider store={store}>
        <div className="cotanier-fluid">
          <SignUp />
        </div>
      </Provider>
    </React.StrictMode>
  );
}

export default App;
