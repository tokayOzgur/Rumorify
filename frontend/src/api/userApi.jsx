import axiosInstance from "./axiosInstance";

export const fetchUsers = (page = 0, size = 3) => {
  return axiosInstance.get("", { params: { page, size } });
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

export const activateUser = (token) => {
  return axiosInstance.patch(`/activation/${token}`);
};
