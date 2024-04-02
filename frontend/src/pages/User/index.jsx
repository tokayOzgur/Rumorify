import { fetchUserById } from "@/api/userApi";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { ProfileCard } from "./components/ProfileCard";

export function User() {
  // TODO: use useRouteParamApiRequest hook
  const { id } = useParams();
  const { t } = useTranslation();
  const [user, setUser] = useState(null);
  const [apiProgress, setApiProgress] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const loadUser = async () => {
    setApiProgress(true);
    try {
      const response = await fetchUserById(id);
      setUser(response.data);
    } catch (error) {
      console.log(error);
      setErrorMessage(t("userNotFound"));
    } finally {
      setApiProgress(false);
    }
  };

  useEffect(() => {
    loadUser();
  }, [id]);

  return (
    <div className="container">
      {errorMessage && <Alert styleType="danger">{errorMessage}</Alert>}
      {apiProgress && <Spinner size="lg m-auto" />}
      {user && (
        <ProfileCard user={user} loadUser={loadUser} />
      )}
    </div>
  );
}
