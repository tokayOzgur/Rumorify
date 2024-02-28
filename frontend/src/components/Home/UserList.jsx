import { fetchUsers } from "@/api/userApi";
import { useCallback, useEffect, useState } from "react";

export const UserList = () => {
  const [userPage, setUserPage] = useState({
    content: [],
    last: false,
    number: 0,
    first: false,
  });

  const getUsers = useCallback(async (page) => {
    const response = await fetchUsers(page);
    setUserPage(response.data);
  }, []);

  useEffect(() => {
    getUsers();
  }, [getUsers]);

  return (
    <div className="container">
      <div className="row">
        <div className="list-group border p-2">
          {userPage.content.map((user) => (
            <a
              href="#"
              className="list-group-item list-group-item-action"
              key={user.id}
            >
              <div className="d-flex w-100 justify-content-between">
                <h5 className="mb-1">{user.name}</h5>
                <small className="text-body-secondary">3 days ago</small>
              </div>
              <p className="mb-1">{user.email}</p>
              <small className="text-body-secondary">
                And some muted small print.
              </small>
            </a>
          ))}
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
        </div>
      </div>
    </div>
  );
};
