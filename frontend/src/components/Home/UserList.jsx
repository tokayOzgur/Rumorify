import { fetchUsers } from "@/api/userApi";
import { Spinner } from "@/shared/components/Spinner";
import { Alert } from "@/shared/components/Alert";
import { useCallback, useEffect, useState } from "react";
import { UserListItem } from "./UserListItem";

export const UserList = () => {
  const [userPage, setUserPage] = useState({
    content: [],
    last: false,
    number: 0,
    first: false,
  });
  const [apiProgress, setApiProgress] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const getUsers = useCallback(async (page) => {
    setApiProgress(true);

    fetchUsers(page)
      .then((response) => {
        setUserPage(response.data);
      })
      .catch((error) => {
        console.log(error);
        // TODO use translate for error message
        setErrorMessage("Something went wrong! " + error.message);
      })
      .finally(() => setApiProgress(false));
  }, []);

  useEffect(() => {
    getUsers();
  }, [getUsers]);

  return (
    <div className="container">
      <div className="row">
        <div className="list-group border p-2">
          {userPage.content.map((user) => (
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
                Previous
              </button>
              <button
                className="btn btn-primary"
                disabled={userPage.last}
                onClick={() => {
                  getUsers(++userPage.number);
                }}
              >
                Next
              </button>
            </div>
          )}
          {errorMessage && <Alert styleType="danger">{errorMessage}</Alert>}
        </div>
      </div>
    </div>
  );
};
