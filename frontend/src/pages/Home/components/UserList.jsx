import { fetchUsers } from "@/api/userApi";
import { Spinner } from "@/shared/components/Spinner";
import { Alert } from "@/shared/components/Alert";
import { useCallback, useEffect, useState } from "react";
import { UserListItem } from "./UserListItem";
import { useTranslation } from "react-i18next";

export const UserList = () => {
  const { t } = useTranslation();
  const [userPage, setUserPage] = useState({
    content: [],
    last: false,
    number: 0,
    first: false,
  });
  const [apiProgress, setApiProgress] = useState(false);

  const getUsers = useCallback(async (page) => {
    setApiProgress(true);
    try {
      const response = await fetchUsers(page);
      setUserPage(response.data);
    } catch (err) {
      toast.error(t("errorOccurred") + err.message);
    } finally {
      setApiProgress(false);
    }
  }, []);

  useEffect(() => {
    getUsers();
  }, [getUsers]);

  return (
    <div className="container">
      <div className="row">
        <div className="list-group border p-2">
          {userPage &&
            userPage.content.map((user) => (
              <UserListItem key={user.id} user={user} />
            ))}
          {apiProgress ? (
            <Spinner size="lg m-auto" />
          ) : (
            <div className="btn-group m-1">
              <button
                className="btn btn-warning"
                disabled={userPage.first}
                onClick={() => {
                  getUsers(--userPage.number);
                }}
              >
                {t("previous")}
              </button>
              <button
                className="btn btn-primary"
                disabled={userPage.last}
                onClick={() => {
                  getUsers(++userPage.number);
                }}
              >
                {t("next")}
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
