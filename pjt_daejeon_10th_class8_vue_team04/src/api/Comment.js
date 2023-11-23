import { localAxios } from "../util/Axios.js";

const local = localAxios();

export const _createComment = async (data) => {
  const response = await local.post(`/board/comment`, data);
  return response.data;
};

export const _getCommentList = async (bno) => {
  const response = await local.get(`/board/comment/${bno}`);
  return response.data;
};

export const _modifyComment = async (data) => {
  const response = await local.put(`/board/comment`, data);
  return response.data;
};

export const _deleteComment = async (cno) => {
  const response = await local.delete(`/board/comment/${cno}`);
  return response.data;
};
