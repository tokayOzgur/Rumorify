import axiosInstance from "./axiosInstance";

export const login = (credentials) => {
  return axiosInstance.post(`/auth`, credentials);
};

export const logout = () => {
  return axiosInstance.delete(`/auth`);
};

export const getCurrentUser = () => {
  return axiosInstance.get(`/auth`);
};
