import axiosInstance from "./axiosInstance";

export const login = (credentials) => {
  return axiosInstance.post(`/auth`, credentials);
};
