import { createPost } from "@/api/postApi";
import { Button } from "@/shared/components/Button";
import { PostMedia } from "@/shared/components/PostMedia";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { toast } from "react-toastify";

export const CreatePost = () => {
  const { t } = useTranslation();
  const [apiProgress, setApiProgress] = useState(false);
  const [postContent, setPostContent] = useState("");
  const [postImage, setPostImage] = useState(null);
  const [postVideo, setPostVideo] = useState(null);

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (!file) return;
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
      const fileType = file.type.split("/")[0];
      if (fileType === "image") setPostImage(fileReader.result);
      else if (fileType === "video") setPostVideo(fileReader.result);
      console.log("postImage::", fileReader.result.substring(0, 100));
    };
    fileReader.readAsDataURL(file);
  };

  const handlePostSubmit = async (event) => {
    event.preventDefault();
    setApiProgress(true);
    if (!postContent) {
      toast.error(t("postContentRequired"));
      setApiProgress(false);
      return;
    }
    const postData = {
      content: postContent,
      image: postImage,
      video: postVideo,
    };
    console.log(postData);
    try {
      await createPost(postData);
      toast.success(t("postCreated"));
      window.location.reload();
      
      setPostContent("");
      setPostImage(null);
      setPostVideo(null);
    } catch (error) {
      toast.error(t("postFailed"));
      toast.error(error.response.data.message);
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <div className="row">
      <div className="col-12">
        <div className="card">
          <div className="card-body">
            <div className="form-group">
              <div className="col-12 d-flex">
                {postImage && (
                  <div className={postVideo ? "col-6" : "col-12"}>
                    <PostMedia
                      className="img-thumbnail"
                      base64src={postImage}
                      alt={"postImage"}
                      width={"100%"}
                    />
                  </div>
                )}
                {postVideo && (
                  <div className={postImage ? "col-6" : "col-12"}>
                    <PostMedia
                      className="img-thumbnail"
                      base64src={postVideo}
                      alt={"postVideo"}
                      width={"100%"}
                    />
                  </div>
                )}
              </div>
              <textarea
                className="form-control"
                placeholder={t("writePost")}
                rows="3"
                disabled={apiProgress}
                value={postContent}
                onChange={(event) => setPostContent(event.target.value)}
              ></textarea>
            </div>
            <div className="d-flex justify-content-end mt-1">
              <input
                className="form-control me-1"
                type="file"
                id="formFile"
                onChange={handleFileChange}
                disabled={apiProgress}
                accept="image/*,video/*"
              />
              <Button
                btnClass="primary"
                apiProgress={apiProgress}
                disabled={apiProgress}
                children={t("send")}
                onClick={handlePostSubmit}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
