import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";

export const Activation = () => {
  const { t } = useTranslation();
  const { token } = useParams();
  return (
    <div className="container">
      <div className="row">
        <div className="col-12">
          <h1>{t("activation")}</h1>
          <p>{token}</p>
        </div>
      </div>
    </div>
  );
};
