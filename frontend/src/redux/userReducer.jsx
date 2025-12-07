// userReducer.js
const initialState = {
    username: "",
    provider: "",
    isLogin: false,
    id: null,
  };
  
  // reducer 함수: state와 action을 받아서 새로운 state 반환
  export default function userReducer(state = initialState, action) {
    switch (action.type) {
      case "SET_USER_NAME":
        return { ...state, username: action.payload };
      case "SET_IS_LOGIN":
        return { ...state, isLogin: action.payload };
      case "SET_ID":
        return { ...state, id: action.payload };
      case "LOGOUT":
        return { ...state, username: "", isLogin: false, id: null, provider: "" };
      case "SET_PROVIDER":
            return { ...state, provider: action.payload };  
      default:
        return state;
    }
  }