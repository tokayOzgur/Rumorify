import { requestPasswordReset } from "@/api/userApi";
import { Alert } from "@/shared/components/Alert";
import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/Input";
import { Spinner } from "@/shared/components/Spinner";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";

export const PasswordResetRequest = () => {
  const [email, setEmail] = useState("");
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { t } = useTranslation();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setApiProgress(true);
    try {
      setErrorMessage("");
      setSuccessMessage("");
      const response = await requestPasswordReset(email);
      console.log("response", response);
      setSuccessMessage(response.data.message);
      setEmail("");
    } catch (error) {
      setErrorMessage(error.response.data.message);
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
          <div className="card p-3">
            <div className="card-header mb-3">
              <h3>{t("resetPassword")}</h3>
            </div>
            <form onSubmit={handleSubmit}>
              <Input
                type="email"
                name="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                label={t("Email")}
              />
              <Button
                disabled={apiProgress}
                apiProgress={apiProgress}
                type="submit"
                onClick={handleSubmit}
                btnClass="primary w-100"
              >
                {t("Submit")}
              </Button>
            </form>
            {apiProgress && <Spinner size="md" />}
            {successMessage && (
              <Alert styleType="success">{successMessage}</Alert>
            )}
            {errorMessage && <Alert styleType="danger">{errorMessage}</Alert>}
          </div>
        </div>
      </div>
    </div>
  );
};
