import React from "react";
import defaultProfileImage from "@/assets/defUser.png";

export const ProfileImage = ({ width, className, alt, src, height, image }) => {
  return (
    <img
      src={src || image || defaultProfileImage}
      alt={alt}
      width={width}
      height={height}
      className={className}
    />
  );
};
