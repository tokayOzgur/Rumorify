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

  const isBase64 = postMedia.startsWith("data:");
  let mediaType;

  if (isBase64) {
    const mimeType = postMedia.split(";")[0].split(":")[1];
    mediaType = mimeType.split("/")[0];
  } else {
    const fileExtension = postMedia.split(".").pop();
    const imageExtensions = ["jpg", "jpeg", "png", "gif"];
    const videoExtensions = ["mp4", "mov", "avi", "webm"];

    if (imageExtensions.includes(fileExtension.toLowerCase())) {
      mediaType = "image";
    } else if (videoExtensions.includes(fileExtension.toLowerCase())) {
      mediaType = "video";
    }
  }

  return mediaType === "image" ? (
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
