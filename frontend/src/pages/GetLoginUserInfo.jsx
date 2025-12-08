import React, { useEffect, useState } from 'react';
import { getLoginUserInfo, deleteUser } from "../api/userAPI";
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setIsLogin } from '../redux/userAction';
import "../css/mypageform.css";

const GetLoginUserInfo = () => {
  const [userInfo, setUserInfo] = useState(null);
    const navigate = useNavigate();
    const dispatch = useDispatch();

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const entity = await getLoginUserInfo(); // 비동기 처리
        setUserInfo(entity.data ? entity.data : entity); 
        // axios라면 entity.data, fetch라면 entity 그대로
      } catch (error) {
        console.error("유저 정보를 불러오지 못했습니다:", error);
      }
    };

    fetchUserInfo();
  }, []); // 컴포넌트가 처음 렌더링될 때 실행

  return (
    <div className="user-info-container">
      <h3>유저정보</h3>
      {userInfo ? (
        <div>
          <div className ="row">
            <div className ="col">
          <p><b>이메일</b> &nbsp;{userInfo.email}</p>
          <p><b>닉네임</b>&nbsp; {userInfo.nickname}</p>
          <p><b>사용자 이름</b>&nbsp; {userInfo.username}</p>
          <p><b>휴대폰</b>&nbsp; {userInfo.phone}</p>
          <p><b>주소</b>&nbsp; {userInfo.address}</p>
          <p><b>역할</b>&nbsp; {userInfo.role}</p>
          {/* <p>프로필: {userInfo.profile}</p>   */}
          </div>
          <div div className ="col">
           {userInfo.provider === null || userInfo.provider === ""? <p><img 
             src={`data:image/png;base64,${userInfo.profile}`} 
            alt="프로필 이미지" /></p>:""
          }
           {userInfo.provider === "GOOGLE"? <p><img 
             src= "/icon/web_neutral_sq_na@4x.png"
            alt="구글 프로필 이미지" /></p>:""
          }
          {userInfo.provider === "NAVER"? <p><img 
             src= "/icon/btnG_icon_square.png"
            alt="네이버 프로필 이미지" /></p>:""
          }
           {userInfo.provider === "KAKAO"? <p><img 
             src= "/icon/kakaotalk_sharing_btn_small.png"
            alt="카카오 프로필 이미지" /></p>:""
          }







            </div>
          </div>
         {userInfo.provider==="" ||userInfo.provider === null? <Link to={`/updateuser/${userInfo.id}`}>회원정보 수정</Link> :""}
          <button type ="button" onClick={() => deleteUser(userInfo.id, userInfo.provider ,navigate, dispatch)}>회원정보 삭제</button> 
        </div>
      ) : (
        <p>로딩 중...</p>
      )}
  
    </div>
  );
};

export default GetLoginUserInfo;























// const GetLoginUserInfo = (props) => {
//   const [userInfo, setUserInfo] = useState(null);
//     const navigate = useNavigate();
//   useEffect(() => {
//     const fetchUserInfo = async () => {
//       try {
//         const entity = await getLoginUserInfo(); // 비동기 처리
//         setUserInfo(entity.data ? entity.data : entity); 
//         // axios라면 entity.data, fetch라면 entity 그대로
//       } catch (error) {
//         console.error("유저 정보를 불러오지 못했습니다:", error);
//       }
//     };

//     fetchUserInfo();
//   }, []); // 컴포넌트가 처음 렌더링될 때 실행

//   return (
//     <div>
//       <h3>유저정보</h3>
//       {userInfo ? (
//         <div>
//           <p>이메일: {userInfo.email}</p>
//           <p>닉네임: {userInfo.nickname}</p>
//           <p>유저 이름: {userInfo.username}</p>
//           <p>휴대폰: {userInfo.phone}</p>
//           <p>주소: {userInfo.address}</p>
//           <p>역할: {userInfo.role}</p>
//           {/* <p>프로필: {userInfo.profile}</p>   */}
//          <p>프로필: <img 
//              src={`data:image/png;base64,${userInfo.profile}`} 
//             alt="프로필 이미지" 
//             /></p>
        
//           <Link to={`/updateuser/${userInfo.id}`}>회원정보 수정</Link> 
//           <button type ="button" onClick={() => deleteUser(userInfo.id, navigate, props.setIsLogin)}>회원정보 삭제</button> 
//         </div>
//       ) : (
//         <p>로딩 중...</p>
//       )}
  
//     </div>
//   );
// };

// export default GetLoginUserInfo;