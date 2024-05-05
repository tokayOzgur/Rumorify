import React from "react";

export const PostListItem = ({ post }) => {
  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    return new Intl.DateTimeFormat("tr-TR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    }).format(date);
  };

  return (
    <div className="row">
      <div className="border rounded mb-1">
        <div className="col-12">
          {post.image && (
            <img
              src={post.image}
              className="img-fluid mw-100"
              alt={`post_${post.id}`}
            />
          )}
        </div>
        <div className="col-12">
          {post.video && (
            <video
              src={post.video}
              className="img-fluid mw-100"
              controls
            ></video>
          )}
        </div>
        <div className="col-12">
          <b>{post.username}</b>
          <p>{post.content}</p>
          <small>{formatDate(post.creationTime)}</small>
        </div>
      </div>
    </div>
  );
};
