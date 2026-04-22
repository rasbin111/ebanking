import axios, { type AxiosResponse } from "axios";

const PUBLIC_BASE_URL = `${import.meta.env.VITE_PUBLIC_BASE_URL}/api/v1`;

const api = axios.create({
  baseURL: PUBLIC_BASE_URL,
  timeout: 1000000,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

const fileApi = axios.create({
  baseURL: PUBLIC_BASE_URL,
  timeout: 4000000,
  headers: {
    "Content-Type": "multipart/form-data",
  },
});

const authenticated = (api: any) => {
  const token: string | null = localStorage.getItem("token");
  let parsedToken: string | null;

  try {
    parsedToken = token ? JSON.parse(token) : null;
  } catch {
    parsedToken = token;
  }

  api.default.headers.common.Authorization = parsedToken || "";
  return api;
};

export const getData = <R = any>(url: string): Promise<AxiosResponse<R>> => authenticated(api).get(url);

export const loginPost = <T, R = any>(
  url: string,
  data: T,
): Promise<AxiosResponse<R>> => api.post<R>(url, data);
