// userActions.js
export const setUsername = (name) => ({
    type: "SET_USER_NAME",
    payload: name,
  });
  
  export const setIsLogin = (isLogin) => ({
    type: "SET_IS_LOGIN",
    payload: isLogin,
  });
  
  export const setId = (id) => ({
    type: "SET_ID",
    payload: id,
  });
  
  export const logout = () => ({
    type: "LOGOUT",
  });