import React, { useState } from "react";
import { loginUser, registerUser } from "../api/userAPI";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";





const Home = () => {
  const {isLogin,provider} = useSelector((state) =>state.user);

  return (
    <div>
   {isLogin?"":<Link to={"/login"}>로그인</Link>}
  {isLogin?<Link to={`/getLoginUserInfo`}>회원정보</Link> :""} 
    {/* {isLogin?<Link to={`/updateuser/${id}`}>회원수정</Link> :""} */}
    {isLogin? "":<Link to={"/join"}>회원가입</Link>}
    </div>
    
  );
};

export default Home;






// const Home = ({isLogin}) => {
  
  


//   return (
//     <div>
//    {isLogin?"":<Link to={"/login"}>로그인</Link>}
//   {isLogin?<Link to={`/getLoginUserInfo`}>회원정보</Link> :""} 
//     {/* {isLogin?<Link to={`/updateuser/${id}`}>회원수정</Link> :""} */}
//     {isLogin? "":<Link to={"/join"}>회원가입</Link>}
//     </div>
    
//   );
// };

// export default Home;