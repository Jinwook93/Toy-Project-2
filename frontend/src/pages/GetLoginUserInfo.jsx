import React, { useEffect, useState } from 'react';
import { getLoginUserInfo, deleteUser } from "../api/userAPI";
import { Link, useNavigate } from 'react-router-dom';

const GetLoginUserInfo = (props) => {
  const [userInfo, setUserInfo] = useState(null);
    const navigate = useNavigate();
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
    <div>
      <h3>유저정보</h3>
      {userInfo ? (
        <div>
          <p>이메일: {userInfo.email}</p>
          <p>닉네임: {userInfo.nickname}</p>
          <p>유저 이름: {userInfo.username}</p>
          <p>휴대폰: {userInfo.phone}</p>
          <p>주소: {userInfo.address}</p>
          <p>역할: {userInfo.role}</p>
          {/* <p>프로필: {userInfo.profile}</p>   */}
         <p>프로필: <img 
             src={`data:image/png;base64,${userInfo.profile}`} 
            alt="프로필 이미지" 
            /></p>
        
          <Link to={`/updateuser/${userInfo.id}`}>회원정보 수정</Link> 
          <button type ="button" onClick={() => deleteUser(userInfo.id, navigate, props.setIsLogin)}>회원정보 삭제</button> 
        </div>
      ) : (
        <p>로딩 중...</p>
      )}
  
    </div>
  );
};

export default GetLoginUserInfo;