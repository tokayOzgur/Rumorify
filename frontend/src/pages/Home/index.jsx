import { UserList } from "@/pages/Home/components/UserList";
import { PostList } from "./components/PostList";
import { CreatePost } from "./components/CreatePost";

export const Home = () => {
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-5 col-sm-12">
          <UserList />
        </div>
        <div className="col-md-7 col-sm-12">
          <div className="row">
            <CreatePost />
          </div>
          <div className="row">
            <PostList />
          </div>
        </div>
      </div>
    </div>
  );
};
