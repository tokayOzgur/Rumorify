import { fetchUsers } from "@/api/userApi";
import { Spinner } from "@/shared/components/Spinner";
import { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { UserListItem } from "./UserListItem";
import { Button } from "@/shared/components/Button";

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
            <Button
              btnClass="outline-secondary border-0"
              apiProgress={apiProgress}
              children={t("previous")}
              disabled={userPage.first}
              onClick={() => {
                getUsers(--userPage.number);
              }}
            />
            <Button
              btnClass="outline-dark border-0"
              apiProgress={apiProgress}
              children={t("next")}
              disabled={userPage.last}
              onClick={() => {
                getUsers(++userPage.number);
              }}
            />
          </div>
        </div>
      )}
      {userPage &&
        userPage.content.map((user) => (
          <div className="col-12" key={user.id}>
            <UserListItem user={user} />
          </div>
        ))}
    </div>
  );
};
