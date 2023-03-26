export interface User {
  login: string;
  password: string;
}

export interface UserTokenInfo {
  userInfo: UserInfo;
}

export interface UserInfo {
  lastName: string;
  firstName: string;
  email: string;
  token: string;
}
