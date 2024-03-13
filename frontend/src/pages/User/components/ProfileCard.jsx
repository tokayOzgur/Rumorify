import React from "react";
import defaultProfileImage from "@/assets/defUser.png";
import { useSelector } from "react-redux";
import { useTranslation } from "react-i18next";

export const ProfileCard = ({ user }) => {
  const authState = useSelector((store) => store.auth);
  const { t } = useTranslation();

  return (
    <div className="card m-5">
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
            <button className="btn btn-info float-end">{t("edit")}</button>
            <h5 className="card-title">{user.username}</h5>
            <p className="card-text">{user.description}</p>
            <p className="card-text">
              <small className="text-muted">
                {user.firstName + " " + user.lastName}
              </small>
            </p>
            <p className="card-text">
              <strong>{user.email}</strong>
            </p>
            <p className="card-text">
              <small className="text-muted">Last updated 3 mins ago</small>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};
