import React, { useState } from "react";
import { loginUser, registerUser } from "./api/userAPI";

const Home = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLogin, setIsLogin] = useState(false);
  const [userName, setUserName] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser(email, password); // Response 객체

      // 헤더 저장
      localStorage.setItem("jwt", response.headers.get("Authorization"));
      localStorage.setItem("refreshJwt", response.headers.get("Refresh-Token"));

      // body 읽기
      const loggedUser = await response.text();
      setUserName(loggedUser);

      alert("로그인 성공!");
      setIsLogin(true);
    } catch (err) {
      alert("로그인 실패");
    }
  };

  return (
    <div>
      <h2>안녕하세요, {isLogin ? userName : "anonymousUser"}님</h2>

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
    </div>
  );
};

export default Home;