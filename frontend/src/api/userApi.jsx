import axiosInstance from "./axiosInstance";

export const fetchUsers = () => {
  return axiosInstance.get("");
};

export const fetchUserById = (id) => {
  return axiosInstance.get(`/${id}`);
};

export const addUser = (userData) => {
  return axiosInstance.post("/addUser", userData);
};

export const updateUser = (id, userData) => {
  return axiosInstance.put(`/update/${id}`, userData);
};

export const deleteUserByID = (id) => {
  return axiosInstance.delete(`/delete/${id}`);
};
