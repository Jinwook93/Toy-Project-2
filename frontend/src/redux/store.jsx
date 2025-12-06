// store.js
import { createStore, combineReducers } from "redux";
import userReducer from "./userReducer";

// 여러 reducer를 합칠 수 있음
const rootReducer = combineReducers({
  user: userReducer,            // 키값 : reducer이름
});

// Redux DevTools 연결
export const store = createStore(
  rootReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
