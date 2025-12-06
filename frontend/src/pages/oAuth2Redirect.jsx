import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setIsLogin, setUsername } from "../redux/userAction";
import { oAuth2UserCheck } from "../api/userAPI";

const OAuth2Redirect = ({provider}) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const data = await oAuth2UserCheck(); // 서버에서 쿠키 기반으로 유저 정보 반환
        dispatch(setUsername(data.user.email));
        localStorage.setItem("jwt", data.accessToken);
        localStorage.setItem("refreshJwt", data.refreshToken);
        console.log(data);
        dispatch(setIsLogin(true));
        alert("로그인에 성공하였습니다 !!");
        navigate("/"); // 로그인 후 홈으로 이동
      } catch (err) {
        console.error("유저 정보 불러오기 실패:", err);
        navigate("/login");
      }
    };

    fetchUserInfo();
  }, [dispatch, navigate]);

  return <div>로그인 처리 중입니다...</div>;
};

export default OAuth2Redirect;