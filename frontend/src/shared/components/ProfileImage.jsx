import React from "react";

export const ProfileImage = ({ width, className, alt, src }) => {
  return <img src={src} alt={alt} width={width} className={className} />;
};
