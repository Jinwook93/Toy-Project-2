import React, { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../css/header.css";
import { logoutUser } from "../api/userAPI";
import { useDispatch, useSelector } from "react-redux";
import { logout, setIsLogin, setProvider, setUsername } from "../redux/userAction";
import { getLoginUserInfo} from "../api/userAPI";


const Header = () => {

  const navigate = useNavigate();
  const {isLogin, username, provider} =  useSelector((state) => state.user);   //store에서 키가 user인 객체를 가져옴
  const dispatch = useDispatch();





  useEffect(() => {
    const fetchUserInfo = async () => {
      const token = localStorage.getItem("jwt");
      const refreshToken = localStorage.getItem("refreshJwt");

      if (token) {
        try {
          const data = await getLoginUserInfo();
          dispatch(setUsername(data.email));
          dispatch(setProvider(data.provider));
          dispatch(setIsLogin(true));
        } catch (err) {
          console.error("유저 정보 불러오기 실패12312:", err);
        }
      }
    };

    fetchUserInfo(); // ✅ async 함수 호출
  }, [dispatch]);













  const handleLogout = async(provider) => {
 
    // const response = await logoutUser(props.id, props.isLogin); 

    const response = await logoutUser(provider); 
    localStorage.removeItem("jwt");
    localStorage.removeItem("refreshJwt");
    localStorage.removeItem(provider.toLowerCase()+"_AccessToken");
    //dispatch(setIsLogin(false));   // ✅ 상태 변경은 dispatch로
    dispatch(logout());            // ✅ 다른 action도 dispatch로
    //dispatch(setProvider(""));
    alert("로그아웃 되었습니다.");
    navigate("/login");
   
    // setIsLogin(false);
    // localStorage.removeItem("jwt"); // 토큰 제거
    // alert("로그아웃 되었습니다.");
  };





  
  return (
    <header className="header">
      <div className="header-container">
        {/* 로고 */}
        <div className="logo">
          <Link to="/">MyWebsite</Link>
        </div>

        {/* 네비게이션 */}
        <nav className="nav">
          <Link to="/join">메뉴1</Link>
         <Link to="/getLoginUserInfo">메뉴2</Link>
         <Link to="/getLoginUserInfo">메뉴3</Link>
        </nav>






        {/* 인사말 */}
        <div className="welcome">

        <div  id="logingreeting">
          {isLogin ? `안녕하세요, ${username} 님`: "로그인이 필요합니다"}
          </div>
           {/* 네비게이션 */}
        <nav className="nav">
          {isLogin?"":<Link to="/join">회원가입</Link>}
          {isLogin? <Link to="/getLoginUserInfo">내 정보</Link>:""}
          {isLogin ? (
            <button className="logout-btn" onClick={() =>{handleLogout(provider)}}>
              로그아웃
            </button>
          ) : (
            <Link to="/login">로그인</Link>
          )}
        </nav>
         
        </div>
      </div>
    </header>
  );
};

export default Header;
















































// const Header = ( props ) => {

//   const navigate = useNavigate();


//   const handleLogout = async() => {
   
//     // const response = await logoutUser(props.id, props.isLogin); 
//     const response = await logoutUser(props.setIsLogin, navigate); 

//     // setIsLogin(false);
//     // localStorage.removeItem("jwt"); // 토큰 제거
//     // alert("로그아웃 되었습니다.");
//   };

//   return (
//     <header className="header">
//       <div className="header-container">
//         {/* 로고 */}
//         <div className="logo">
//           <Link to="/">MyWebsite</Link>
//         </div>

//         {/* 네비게이션 */}
//         <nav className="nav">
//           <Link to="/join">회원가입</Link>
//           <Link to="/getLoginUserInfo">내 정보</Link>
//           {props.isLogin ? (
//             <button className="logout-btn" onClick={handleLogout}>
//               로그아웃
//             </button>
//           ) : (
//             <Link to="/login">로그인</Link>
//           )}
//         </nav>

//         {/* 인사말 */}
//         <div className="welcome">
//           안녕하세요, {props.isLogin ? props.username : "anonymousUser"}님
//         </div>
//       </div>
//     </header>
//   );
// };

// export default Header;