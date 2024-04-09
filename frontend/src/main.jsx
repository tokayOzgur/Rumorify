import ReactDOM from "react-dom/client";
import { RouterProvider } from "react-router-dom";
import "@/locales";
import router from "@/router";
import { store } from "./app/store";
import { Provider } from "react-redux";

ReactDOM.createRoot(document.getElementById("root")).render(
  <Provider store={store}>
    <RouterProvider router={router} />
  </Provider>
);
