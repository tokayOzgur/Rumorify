import { activateUser } from "@/api/userApi";
import { Spinner } from "@/shared/components/Spinner";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";

export const Activation = () => {
  //TODO: use useRouteParamApiRequest hook
  const { t } = useTranslation();
  const { token } = useParams();
  const [apiProgress, setApiProgress] = useState(false);

  useEffect(() => {
    async function activateAccount() {
      try {
        const response = await activateUser(token);
        toast.success(response.data.message);
      } catch (axiosError) {
        toast.error(axiosError.response.data.message);
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
        </div>
      </div>
    </div>
  );
};
