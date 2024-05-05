import { getAllPost } from "@/api/postApi";
import { PostListItem } from "@/shared/components/PostListItem";
import { Spinner } from "@/shared/components/Spinner";
import React, { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { toast } from "react-toastify";

export const PostList = () => {
  const { t } = useTranslation();
  const [apiProgress, setApiProgress] = useState(false);
  const [postPage, setPostPage] = useState({
    content: [],
    last: false,
    number: 0,
    first: false,
  });

  const getPosts = useCallback(async (page) => {
    setApiProgress(true);
    try {
      const response = await getAllPost(page);
      setPostPage(response.data);
    } catch (error) {
      toast.error(t("errorOccurred") + error.message);
    } finally {
      setApiProgress(false);
    }
  }, []);

  useEffect(() => {
    getPosts();
  }, [getPosts]);

  return (
    <div className="row">
      {apiProgress ? (
        <Spinner size="lg m-auto" />
      ) : (
        <div className="col-12">
          <div className="">
            <button
              className="btn btn-outline-secondary border-0"
              disabled={postPage.first}
              onClick={() => {
                getPosts(--postPage.number);
              }}
            >
              {t("previous")}
            </button>
            <button
              className="btn btn-outline-dark border-0"
              disabled={postPage.last}
              onClick={() => {
                getPosts(++postPage.number);
              }}
            >
              {t("next")}
            </button>
          </div>
        </div>
      )}
      {postPage &&
        postPage.content.map((post) => (
          <div className="col-12" key={post.id}>
            <PostListItem post={post} />
          </div>
        ))}
    </div>
  );
};
