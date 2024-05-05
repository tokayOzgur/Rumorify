import { getAllPost, getPostListByUserId } from "@/api/postApi";
import { Button } from "@/shared/components/Button";
import { PostListItem } from "@/shared/components/PostListItem";
import { Spinner } from "@/shared/components/Spinner";
import React, { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { toast } from "react-toastify";

export const PostList = ({ userId }) => {
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
      const response = await getPostListByUserId([userId], page);
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
            <Button
              btnClass="outline-secondary border-0"
              apiProgress={apiProgress}
              children={t("previous")}
              disabled={postPage.first}
              onClick={() => {
                getPosts(--postPage.number);
              }}
            />
            <Button
              btnClass="outline-dark border-0"
              apiProgress={apiProgress}
              children={t("next")}
              disabled={postPage.last}
              onClick={() => {
                getPosts(++postPage.number);
              }}
            />
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
