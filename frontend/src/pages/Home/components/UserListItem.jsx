import defaultProfileImage from "@/assets/defUser.png";
import { ProfileImage } from "@/shared/components/ProfileImage";
import { Link } from "react-router-dom";

export const UserListItem = ({ user }) => {
  return (
    <Link
      className="list-group-item list-group-item-action"
      key={user.id}
      to={`/user/${user.id}`}
    >
      <div className="row">
        <div className="col-2">
          <ProfileImage
            src={user.image ? user.image : defaultProfileImage}
            width={40}
            className={"img-fluid rounded-circle"}
            alt={`image_${user.username}`}
          />
        </div>
        <div className="col-10 ">
          <div className="d-flex w-100 justify-content-between">
            <h5 className="mb-1">{user.username}</h5>
            <h6 className="mb-1">{user.firstName + " " + user.lastName}</h6>
            <small className="text-body-secondary">3 days ago</small>
          </div>
        </div>
        <div className="col-12">
          <i className="mb-1">{user.email}</i>
          <small className="text-body-secondary mx-5 ">
            {user.profileDescription}
          </small>
        </div>
      </div>
    </Link>
  );
};
