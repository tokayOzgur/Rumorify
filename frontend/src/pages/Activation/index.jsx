import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { activateUser } from "@/api/user/userApi";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";

export const Activation = () => {
  //TODO: use useRouteParamApiRequest hook
  const { t } = useTranslation();
  const { token } = useParams();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    async function activateAccount() {
      try {
        const response = await activateUser(token);
        setSuccessMessage(response.data.message);
      } catch (axiosError) {
        setErrorMessage(axiosError.response.data.message);
      } finally {
        setApiProgress(false);
      }
    }
    activateAccount();
  }, [token]);
  return (
    <div className="container">
      <div className="row">
        <div className="col-12">
          <h1>{t("Activation")}</h1>
          {apiProgress && <Spinner size="md" />}
          {successMessage && (
            <Alert styleType="success">{successMessage}</Alert>
          )}
          {errorMessage && <Alert styleType="danger">{errorMessage}</Alert>}
        </div>
      </div>
    </div>
  );
};
