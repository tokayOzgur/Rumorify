import React from "react";
import defaultProfileImage from "@/assets/defUser.png";

export const ProfileImage = ({ width, height, className, alt, src }) => {
  const baseURL = import.meta.env.VITE_APP_WS_BASE_URL;
  const profileImage = src
    ? `${baseURL}/assets/profiles/${src}`
    : defaultProfileImage;
  return (
    <img
      src={profileImage}
      alt={alt}
      width={width}
      height={height}
      className={className}
      onError={(e) => {
        e.target.src = defaultProfileImage;
      }}
    />
  );
};
