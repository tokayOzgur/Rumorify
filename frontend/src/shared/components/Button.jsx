import React from "react";
import { Spinner } from "./Spinner";

export const Button = ({
  btnClass = "primary",
  apiProgress,
  disabled,
  children,
  onClick,
}) => {
  return (
    <button
      className={`btn btn-${btnClass}`}
      disabled={apiProgress || disabled}
      onClick={onClick}
    >
      {apiProgress && <Spinner size="sm" />}
      {children}
    </button>
  );
};
