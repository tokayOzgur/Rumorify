import { PostMedia } from "./PostMedia";

export const PostListItem = ({ post }) => {
  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    return new Intl.DateTimeFormat("tr-TR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
      hour12: false,
    }).format(date);
  };
  console.log("post::", post);
  return (
    <div className="border rounded mb-1">
      <div className="row p-1">
        <div className="col-5">
          <b>{post.username}</b>
          <p>{post.content}</p>
          <small>{formatDate(post.creationTime)}</small>
        </div>
        <div className="col-7">
          <div className="row">
            {post.imageUrl && (
              <div className={post.videoUrl ? "col-6" : "col-12"}>
                <PostMedia
                  className="img-thumbnail"
                  alt={"postImage"}
                  width={"100%"}
                  src={post.imageUrl}
                />
              </div>
            )}
            {post.videoUrl && (
              <div className={post.imageUrl ? "col-6" : "col-12"}>
                <PostMedia
                  className="img-thumbnail"
                  alt={"postVideo"}
                  width={"100%"}
                  src={post.videoUrl}
                />
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
