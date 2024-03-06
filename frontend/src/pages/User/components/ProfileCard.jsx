import React from "react";
import defaultProfileImage from "@/assets/defUser.png";

export const ProfileCard = ({ user }) => {
  return (
    <div className="container m-5">
      <div className="card mb-3" style={{ maxWidth: "540px" }}>
        <div className="row g-0">
          <div className="col-md-4">
            <img
              src={user.image ? user.image : defaultProfileImage}
              className="img-fluid rounded-start"
              alt={`image_${user.username}`}
            />
          </div>
          <div className="col-md-8">
            <div className="card-body">
              <h5 className="card-title">{user.username}</h5>
              <p className="card-text">{user.description}</p>
              <p className="card-text">
                <i>{user.firstName + " " + user.lastName}</i>
              </p>
              <p className="card-text">
                <strong>{user.email}</strong>
              </p>
              <p className="card-text">
                <small className="text-body-secondary">
                  Last updated 3 mins ago
                </small>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
