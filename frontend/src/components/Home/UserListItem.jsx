import defaultProfileImage from "@/assets/defUser.png";

export const UserListItem = ({ user }) => {
  return (
    <a
      href="#"
      className="list-group-item list-group-item-action"
      key={user.id}
    >
      <div className="row">
        <div className="col-2">
          <img
            src={defaultProfileImage}
            className="img-fluid rounded-circle"
            width={40}
          />
        </div>
        <div className="col-10 ">
          <div className="d-flex w-100 justify-content-between">
            <h5 className="mb-1">{user.username}</h5>
            <h6 className="mb-1">{user.firstName + " " + user.lastName}</h6>
            <small className="text-body-secondary">3 days ago</small>
          </div>
        </div>
      </div>
      <i className="mb-1">{user.email}</i>
      <small className="text-body-secondary">{user.profileDescription}</small>
    </a>
  );
};
