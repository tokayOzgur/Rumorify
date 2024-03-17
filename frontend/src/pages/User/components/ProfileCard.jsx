import React, { useState } from "react";
import defaultProfileImage from "@/assets/defUser.png";
import { useDispatch, useSelector } from "react-redux";
import { useTranslation } from "react-i18next";
import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/Input";
import { updateUser } from "@/api/userApi";
import { Alert } from "@/shared/components/Alert";
import { userUpdateSuccess } from "@/features/auth/authSlice";

export const ProfileCard = ({ user }) => {
  const authState = useSelector((store) => store.auth);
  const { t } = useTranslation();
  const [apiProgress, setApiProgress] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const isEditable = !editMode && authState.id === user.id;
  const [newUsername, setNewUsername] = useState(user.username);
  const [newProfileDescription, setNewProfileDescription] = useState(
    user.profileDescription
  );
  const [newFirstName, setNewFirstName] = useState(user.firstName);
  const [newLastName, setNewLastName] = useState(user.lastName);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const dispatch = useDispatch();

  const handleUpdate = () => {
    setApiProgress(true);
    setErrorMessage("");
    setSuccessMessage("");
    user.username = newUsername;
    user.profileDescription = newProfileDescription;
    user.firstName = newFirstName;
    user.lastName = newLastName;
    updateUser(user.id, user)
      .then((response) => {
        setSuccessMessage(response.data.message);
        dispatch(userUpdateSuccess(user));
      })
      .catch((error) => {
        console.log("error::", error);
        setErrorMessage(error.response.data.validationError);
      })
      .finally(() => {
        setApiProgress(false);
        setEditMode(false);
      });
  };

  return (
    <div className="card m-5">
      {errorMessage && <Alert styleType="danger" children={errorMessage} />}
      {successMessage && (
        <Alert styleType="success" children={successMessage} />
      )}
      <div className="row no-gutters">
        <div className="col-md-4">
          <img
            src={user.image ? user.image : defaultProfileImage}
            className="card-img"
            alt={`image_${user.username}`}
          />
        </div>
        <div className="col-md-8">
          <div className="card-body">
            {isEditable && (
              <Button
                btnClass={"info float-end mb-1"}
                onClick={() => {
                  setEditMode(true);
                }}
              >
                {t("edit")}
              </Button>
            )}

            {editMode ? (
              <>
                <Input
                  label={t("username")}
                  type={"text"}
                  id={user.username}
                  defaultValue={user.username}
                  onChange={(e) => {
                    setNewUsername(e.target.value);
                  }}
                />
                <Input
                  label={t("description")}
                  type={"text"}
                  id={user.profileDescription}
                  defaultValue={user.profileDescription}
                  onChange={(e) => {
                    setNewProfileDescription(e.target.value);
                  }}
                />
                <Input
                  label={t("firstName")}
                  type={"text"}
                  id={user.firstName}
                  defaultValue={user.firstName}
                  onChange={(e) => {
                    setNewFirstName(e.target.value);
                  }}
                />
                <Input
                  label={t("lastName")}
                  type={"text"}
                  id={user.lastName}
                  defaultValue={user.lastName}
                  onChange={(e) => {
                    setNewLastName(e.target.value);
                  }}
                />
                <Button
                  btnClass={"primary float-end my-2"}
                  apiProgress={apiProgress}
                  children={t("save")}
                  onClick={handleUpdate}
                />
                <Button
                  btnClass={"outline-secondary mx-2 my-2 float-end"}
                  onClick={() => {
                    setEditMode(false);
                  }}
                  children={t("cancel")}
                />
              </>
            ) : (
              <>
                <span className="badge text-bg-secondary mb-3">3m ago</span>
                <h5 className="card-title">{user.username}</h5>
                <p className="card-text">{user.profileDescription}</p>
                <p className="card-text">
                  <small className="text-muted">
                    {user.firstName + " " + user.lastName}
                  </small>
                </p>
                <p className="card-text">
                  <strong>{user.email}</strong>
                </p>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
