import { fetchUsers } from "@/api/userApi";
import { Spinner } from "@/shared/components/Spinner";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { UserListItem } from "./UserListItem";

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
    <div className="row">
      {apiProgress ? (
        <Spinner size="lg m-auto" />
      ) : (
        <div className="col-12">
          <div className="btn-group">
            <button
              className="btn btn-outline-secondary border-0"
              disabled={userPage.first}
              onClick={() => {
                getUsers(--userPage.number);
              }}
            >
              {t("previous")}
            </button>
            <button
              className="btn btn-outline-dark border-0"
              disabled={userPage.last}
              onClick={() => {
                getUsers(++userPage.number);
              }}
            >
              {t("next")}
            </button>
          </div>
        </div>
      )}
      {userPage &&
        userPage.content.map((user) => (
          <div className="col-12" key={user.id}>
            <UserListItem  user={user} />
          </div>
        ))}
    </div>
  );
};
