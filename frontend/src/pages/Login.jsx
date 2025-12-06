import React, { useState } from "react";
import { loginUser, registerUser } from "../api/userAPI";
import Header from "../components/Header";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setUsername, setIsLogin } from "../redux/userAction";
import "../css/loginform.css";


const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  // const [isLogin, setIsLogin] = useState(false);
  // const [userName, setUserName] = useState("");
  const navigate = useNavigate();   // ✅ 훅을 최상단에서 호출
  const dispatch = useDispatch();



  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser(email, password); // Response 객체

      // 헤더 저장
      localStorage.setItem("jwt", response.headers.get("Authorization").replace("Bearer ", ""));
      localStorage.setItem("refreshJwt", response.headers.get("Refresh-Token"));

      // body 읽기
      const loggedUser = await response.text();
     // setUserName(loggedUser);
     // setIsLogin(true);
      dispatch(setUsername(loggedUser));
      dispatch(setIsLogin(true));   // ✅ 상태 변경은 dispatch로

      alert("로그인 성공!");
      navigate("/");
    } catch (err) {
      alert("로그인 실패");
    }
  };

  return (
    <>
    <div className="login-container">
    


      <form onSubmit={handleLogin}>
        <h3>로그인</h3>
        <input
          type="text"
          placeholder="이메일"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">로그인</button>
      </form>
      <Link to={"/join"}>회원가입</Link>



    </div>

     {/* ✅ OAuth2 로그인 버튼들 */}
 <div className="oauth-buttons">
 <button
   type="button"
   className="google-btn"
   onClick={() => window.location.href = "http://localhost:8080/oauth2/authorization/google"}
 >
   구글 로그인
 </button>

 <button
   type="button"
   className="naver-btn"
   onClick={() => window.location.href = "http://localhost:8080/oauth2/authorization/naver"}
 >
   네이버 로그인
 </button>

 <button
   type="button"
   className="kakao-btn"
   onClick={() => window.location.href = "http://localhost:8080/oauth2/authorization/kakao"}
 >
   카카오 로그인
 </button>
</div>

</>
  );
};

export default Login;














































// const Login = ({setIsLogin, setUserName}) => {
//   const [email, setEmail] = useState("");
//   const [password, setPassword] = useState("");
//   // const [isLogin, setIsLogin] = useState(false);
//   // const [userName, setUserName] = useState("");
//   const navigate = useNavigate();   // ✅ 훅을 최상단에서 호출
//   const dispatch = useDispatch();



//   const handleLogin = async (e) => {
//     e.preventDefault();
//     try {
//       const response = await loginUser(email, password); // Response 객체

//       // 헤더 저장
//       localStorage.setItem("jwt", response.headers.get("Authorization").replace("Bearer ", ""));
//       localStorage.setItem("refreshJwt", response.headers.get("Refresh-Token"));

//       // body 읽기
//       const loggedUser = await response.text();
//       setUserName(loggedUser);
//       setIsLogin(true);
//       //dispatch(setUserName(loggedUser));
//      // dispatch(setIsLogin(true));   // ✅ 상태 변경은 dispatch로

//       alert("로그인 성공!");
//       navigate("/");
//     } catch (err) {
//       alert("로그인 실패");
//     }
//   };

//   return (
//     <div>


//       <form onSubmit={handleLogin}>
//         <h3>로그인</h3>
//         <input
//           type="text"
//           placeholder="이메일"
//           value={email}
//           onChange={(e) => setEmail(e.target.value)}
//         />
//         <input
//           type="password"
//           placeholder="비밀번호"
//           value={password}
//           onChange={(e) => setPassword(e.target.value)}
//         />
//         <button type="submit">로그인</button>
//       </form>
//       <Link to={"/join"}>회원가입</Link>
//     </div>
//   );
// };

// export default Login;