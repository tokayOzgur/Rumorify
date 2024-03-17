import axiosInstance from "./axiosInstance";

export const fetchUsers = (page = 0, size = 3) => {
  return axiosInstance.get("/users", { params: { page, size } });
};

export const fetchUserById = (id) => {
  return axiosInstance.get(`/users/${id}`);
};

export const addUser = (userData) => {
  return axiosInstance.post("/users", userData);
};

export const updateUser = (id, userData) => {
  return axiosInstance.put(`/users/${id}`, userData);
};

export const deleteUserByID = (id) => {
  return axiosInstance.delete(`/users/delete/${id}`);
};

export const activateUser = (token) => {
  return axiosInstance.patch(`/users/activation/${token}`);
};
