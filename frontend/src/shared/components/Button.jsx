import React from "react";
import { Spinner } from "./Spinner";

export const Button = ({ btnClass, apiProgress, disabled, children }) => {
  return (
    <button className="btn btn-primary" disabled={apiProgress || disabled}>
      {apiProgress && <Spinner size="sm" />}
      {children}
    </button>
  );
};
