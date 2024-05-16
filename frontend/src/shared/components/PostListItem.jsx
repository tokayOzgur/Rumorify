import { deletePost, updatePost } from "@/api/postApi";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import { Button } from "./Button";
import { PostMedia } from "./PostMedia";

export const PostListItem = ({ post }) => {
  const authState = useSelector((store) => store.auth);
  const isEditable = authState.username === post.username;
  const [editMode, setEditMode] = useState(false);
  const [apiProgress, setApiProgress] = useState(false);
  const [postContent, setPostContent] = useState(post.content);
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
    setApiProgress(true);
    try {
      await deletePost(id);
      window.location.reload();
      toast.success(t("postDeleted"));
    } catch (error) {
      toast.error(t("postDeleteFailed"));
      toast.error(error.response.data.message);
    }
  };

  const handleUpdatePost = async () => {
    setApiProgress(true);
    const postData = {
      id: post.id,
      content: postContent,
      imageUrl: post.imageUrl,
      videoUrl: post.videoUrl,
    };
    try {
      await updatePost(postData);
      toast.success(t("postUpdated"));
      window.location.reload();
    } catch (error) {
      toast.error(t("postUpdateFailed"));
      console.log(error);
      toast.error(error.response.data.message);
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <div className="border rounded mb-1">
      <div className="row p-1">
        <>
          <div className="col-5">
            <div className="row">
              <div className="col-12">
                {isEditable && (
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
                          btnClass="primary dropdown-item"
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
                {editMode ? (
                  <div className="row">
                    <div className="col-12 h-100">
                      <textarea
                        className="form-control w-100"
                        disabled={apiProgress}
                        value={postContent}
                        onChange={(event) => setPostContent(event.target.value)}
                      ></textarea>
                    </div>
                    <div className="row my-1">
                      <div className="col-6">
                        <Button
                          btnClass="secondary"
                          apiProgress={apiProgress}
                          disabled={apiProgress}
                          onClick={() => {
                            setEditMode(false);
                          }}
                          children={t("cancel")}
                        />
                      </div>
                      <div className="col-6">
                        <Button
                          btnClass="primary "
                          apiProgress={apiProgress}
                          disabled={apiProgress}
                          onClick={handleUpdatePost}
                          children={t("update")}
                        />
                      </div>
                    </div>
                  </div>
                ) : (
                  <p>{post.content}</p>
                )}
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
        </>
      </div>
    </div>
  );
};
