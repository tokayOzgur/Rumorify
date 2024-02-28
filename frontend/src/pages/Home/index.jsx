import { UserList } from "@/components/Home/UserList";

export const Home = () => {
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 col-sm-12">
          <UserList />
        </div>
        <div className="col-md-6 col-sm-12"></div>
      </div>
    </div>
  );
};
