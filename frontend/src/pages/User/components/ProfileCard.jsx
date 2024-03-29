import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useTranslation } from "react-i18next";
import { Button } from "@/shared/components/Button";
import { Input } from "@/shared/components/Input";
import { updateUser } from "@/api/userApi";
import { Alert } from "@/shared/components/Alert";
import { userUpdateSuccess } from "@/features/auth/authSlice";
import { ProfileImage } from "@/shared/components/ProfileImage";

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
  const [newImage, setNewImage] = useState(user.image);
  const [errorMessage, setErrorMessage] = useState({});
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
    user.image = newImage;
    updateUser(user.id, user)
      .then((response) => {
        setSuccessMessage(response.data.message);
        dispatch(userUpdateSuccess(user));
        setEditMode(false);
      })
      .catch((error) => {
        setErrorMessage(error.response.data.validationErrors);
        console.log(errorMessage);
      })
      .finally(() => {
        setApiProgress(false);
      });
  };

  const updateUserImage = (e) => {
    if (e.target.files.length < 1) {
      return;
    }
    const file = e.target.files[0];
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
      setNewImage(fileReader.result);
    };
    fileReader.readAsDataURL(file);
  };

  const handeleCancel = () => {
    setEditMode(false);
    setErrorMessage("");
    setSuccessMessage("");
    setNewUsername(user.username);
    setNewProfileDescription(user.profileDescription);
    setNewFirstName(user.firstName);
    setNewLastName(user.lastName);
    setNewImage(user.image);
  };

  return (
    // TODO: Add a component for the edit form
    // TODO2: Add a component for the info profile
    // TODO3: Check validation for the form
    <div className="card m-5">
      {errorMessage.image && (
        <Alert styleType="danger" children={errorMessage.image} />
      )}
      {successMessage && (
        <Alert styleType="success" children={successMessage} />
      )}
      <div className="row no-gutters">
        <div className="col-md-4">
          <ProfileImage
            src={newImage}
            alt={`image_${user.username}`}
            width={200}
            className="card-img"
          />
          {editMode && (
            <Input
              id={"profileImageId"}
              type={"file"}
              label={t("profileImage")}
              onChange={(e) => {
                updateUserImage(e);
              }}
            />
          )}
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
                  error={errorMessage.username}
                />
                <Input
                  label={t("description")}
                  type={"text"}
                  id={user.profileDescription}
                  defaultValue={user.profileDescription}
                  onChange={(e) => {
                    setNewProfileDescription(e.target.value);
                  }}
                  error={errorMessage.profileDescription}
                />
                <Input
                  label={t("firstName")}
                  type={"text"}
                  id={user.firstName}
                  defaultValue={user.firstName}
                  onChange={(e) => {
                    setNewFirstName(e.target.value);
                  }}
                  error={errorMessage.firstName}
                />
                <Input
                  label={t("lastName")}
                  type={"text"}
                  id={user.lastName}
                  defaultValue={user.lastName}
                  onChange={(e) => {
                    setNewLastName(e.target.value);
                  }}
                  error={errorMessage.lastName}
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
                    handeleCancel();
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
