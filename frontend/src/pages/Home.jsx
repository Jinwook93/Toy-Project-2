import React, { useState } from "react";
import { loginUser, registerUser } from "../api/userAPI";
import { Link } from "react-router-dom";

const Home = ({isLogin}) => {
  

  return (
    <div>
    <Link to={"/login"}>로그인</Link>
    {isLogin? "":<Link to={"/join"}>회원가입</Link>}
    </div>
    
  );
};

export default Home;