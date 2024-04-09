import { useCallback } from "react";
// import { loadAuthState } from "../state/storage";
import { logout } from "@/api/authApi";
import { deleteUserByID } from "@/api/userApi";
import { logoutSuccess } from "@/features/auth/authSlice";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

export const useUserDelete = () => {
  const { id } = useSelector((store) => store.auth);
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
