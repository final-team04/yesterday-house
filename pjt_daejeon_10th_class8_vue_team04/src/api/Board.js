import { localAxios } from "../util/Axios.js";

const local = localAxios();

export const _createBoard = async (data) => {
  const response = await local.post(`/board`, data);
  return response.data;
};

export const _getBoardList = async (aptCode) => {
  const response = await local.get(`/board?aptCode=${aptCode}`);
  return response.data;
};

export const _modifyBoard = async (data) => {
  const response = await local.put(`/board`, data);
  return response.data;
};

export const _deleteBoard = async (bno) => {
  const response = await local.delete(`/board/${bno}`);
  return response.data;
};

export const _getNoticeList = async () => {
  const response = await local.get(`/board/notice`);
  return response.data;
};
