import { Button } from "@/shared/components/Button";
import { useUserDelete } from "@/shared/hooks/useUserDelete";
import { useTranslation } from "react-i18next";

export const UserDelete = ({ id }) => {
  const { t } = useTranslation();
  const { apiProgress, handleDelete } = useUserDelete();

  return (
    <>
      <Button
        btnClass={"outline-danger mx-2 my-2 float-end"}
        onClick={() => {
          handleDelete();
        }}
        apiProgress={apiProgress}
        children={t("deleteAccount")}
      />
    </>
  );
};
