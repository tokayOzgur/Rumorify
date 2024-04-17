import { fetchUserById } from "@/api/userApi";
import { Spinner } from "@/shared/components/Spinner";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { ProfileCard } from "./components/ProfileCard";
import { toast } from "react-toastify";

export function User() {
  // TODO: use useRouteParamApiRequest hook
  const { id } = useParams();
  const { t } = useTranslation();
  const [user, setUser] = useState(null);
  const [apiProgress, setApiProgress] = useState(false);

  const loadUser = async () => {
    setApiProgress(true);
    try {
      const response = await fetchUserById(id);
      setUser(response.data);
    } catch (error) {
      console.log(error);
      toast.error(t("userNotFound"));
    } finally {
      setApiProgress(false);
    }
  };

  useEffect(() => {
    loadUser();
  }, [id]);

  return (
    <div className="container">
      {apiProgress && <Spinner size="lg m-auto" />}
      {user && <ProfileCard user={user} loadUser={loadUser} />}
    </div>
  );
}
