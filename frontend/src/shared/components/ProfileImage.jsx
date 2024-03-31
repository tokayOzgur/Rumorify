import React from "react";
import defaultProfileImage from "@/assets/defUser.png";

export const ProfileImage = ({
  width,
  height,
  className,
  alt,
  src,
  tempSrc,
}) => {
  const profileImage = src ? `/assets/profiles/${src}` : defaultProfileImage;
  console.log("profileImage", profileImage);
  return (
    <img
      src={tempSrc || profileImage}
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
