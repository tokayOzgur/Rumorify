import { useSelector } from "react-redux";
import { Button } from "./Button";
import { PostMedia } from "./PostMedia";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { toast } from "react-toastify";
import { deletePost } from "@/api/postApi";

export const PostListItem = ({ post }) => {
  const authState = useSelector((store) => store.auth);
  const isEditable = authState.id === post.userId;
  const [editMode, setEditMode] = useState(false);
  const { t } = useTranslation();

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

  const handleDelete = async (id) => {
    try {
      await deletePost(id);
      window.location.reload();
      toast.success(t("postDeleted"));
    } catch (error) {
      toast.error(t("postDeleteFailed"));
      toast.error(error.response.data.message);
    }
  };

  return (
    <div className="border rounded mb-1">
      <div className="row p-1">
        <div className="col-5">
          <div className="row">
            <div className="col-12">
              {!isEditable && (
                <div>
                  <button
                    className="btn btn-sm dropdown-toggle"
                    type="button"
                    id="dropdownMenuButton"
                    data-bs-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false"
                  ></button>
                  <ul className="dropdown-menu">
                    <li>
                      <Button
                        btnClass=" dropdown-item"
                        onClick={() => {
                          setEditMode(true);
                        }}
                        children={t("edit")}
                      />
                    </li>
                    <li>
                      <Button
                        btnClass="danger dropdown-item"
                        onClick={() => {
                          handleDelete(post.id);
                        }}
                        children={t("delete")}
                      />
                    </li>
                  </ul>
                  <b>{post.username}</b>
                </div>
              )}
              <p>{post.content}</p>
              <small>{formatDate(post.creationTime)}</small>
            </div>
          </div>
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
