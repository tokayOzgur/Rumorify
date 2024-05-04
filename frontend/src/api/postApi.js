import axiosInstance from "./axiosInstance";

/*
    export const fetchUsers = (page = 0, size = 3) => {
  return axiosInstance.get("/users", { params: { page, size } });
};
*/

export const createPost = (postData) => {
  return axiosInstance.post("/posts", postData);
};

export const updatePost = (postData) => {
  return axiosInstance.put(`/posts/${postData.id}`, postData);
};

export const deletePost = (id) => {
  return axiosInstance.delete(`/posts/${id}`);
};

export const getAllPost = (page = 0, size = 5) => {
  return axiosInstance.get("/posts", { params: { page, size } });
};

export const getPostByPostId = (id) => {
  return axiosInstance.get(`/posts/${id}`);
};

export const getPostListByUserId = (ids, page = 0, size = 5) => {
  return axiosInstance.get(`/posts/users`, { params: { ids, page, size } });
};
