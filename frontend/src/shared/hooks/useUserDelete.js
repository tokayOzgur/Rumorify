import React, { useCallback } from "react";
import { loadAuthState } from "../state/storage";
import { deleteUserByID } from "@/api/userApi";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { logoutSuccess } from "@/features/auth/authSlice";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { logout } from "@/api/authApi";

export const useUserDelete = () => {
  const { id } = loadAuthState();
  const [apiProgress, setApiProgress] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const handleDelete = useCallback(async () => {
    const result = confirm(t("deleteAccountConfirmation"));
    if (!result) {
      return;
    }
    setApiProgress(true);
    try {
      await deleteUserByID(id);
      await logout();
      dispatch(logoutSuccess());
      navigate("/login");
    } catch (error) {
      console.error(error);
    } finally {
      setApiProgress(false);
    }
  }, [id]);
  return {
    apiProgress,
    handleDelete,
  };
};
