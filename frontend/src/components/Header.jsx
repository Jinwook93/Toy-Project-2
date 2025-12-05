import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "../css/Header.css";
import { logoutUser } from "../api/userAPI";

const Header = ( props ) => {

  const navigate = useNavigate();

  const handleLogout = async() => {
   
    // const response = await logoutUser(props.id, props.isLogin); 
    const response = await logoutUser(props.setIsLogin, navigate); 

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
          <Link to="/join">회원가입</Link>
          <Link to="/getLoginUserInfo">내 정보</Link>
          {props.isLogin ? (
            <button className="logout-btn" onClick={handleLogout}>
              로그아웃
            </button>
          ) : (
            <Link to="/login">로그인</Link>
          )}
        </nav>

        {/* 인사말 */}
        <div className="welcome">
          안녕하세요, {props.isLogin ? props.username : "anonymousUser"}님
        </div>
      </div>
    </header>
  );
};

export default Header;