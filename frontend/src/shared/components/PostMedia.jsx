import React from "react";

export const PostMedia = ({
  width,
  height,
  className,
  alt,
  src,
  base64src,
}) => {
  const baseURL = import.meta.env.VITE_APP_WS_BASE_URL;
  const postMedia = base64src || `${baseURL}/assets/posts/${src}`;

  const mediaType = postMedia.split(".")[1];
  return mediaType !== "mp4" ? (
    <img
      src={postMedia}
      alt={alt}
      style={{ width, height: height || width }}
      className={className}
    />
  ) : (
    <video
      src={postMedia}
      alt={alt}
      style={{ width, height: height || width }}
      className={className}
      controls
    />
  );
};
