import React from "react";
import defaultProfileImage from "@/assets/defUser.png";

export const ProfileImage = ({ width, height, className, alt, src }) => {
  const profileImage = src
    ? `http://localhost:8080/assets/profiles/${src}`
    : defaultProfileImage;
  console.log("profileImage", profileImage);
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
