import React, { useState } from "react";
import { registerUser } from "../api/userAPI";
import { useNavigate } from "react-router-dom";

const Join = () => {
  const [user, setUser] = useState({
    email: "",
    password: "",
    password_check: "",
    address: "",
    phone: "",
    nickname: "",
    username: "",
    role: "USER",
    isJoin: false
  });
  const [profile,setProfile]= useState("");
  const navigate = useNavigate();
  
  const handleJoin = async (e) => {
    e.preventDefault();

    if(e.target.password.value !== e.target.password_check.value){
      alert("비밀번호와 확인값이 같지 않습니다.")
      return;
    }


    try {
      // 회원가입 API 호출 시 필요한 모든 값 전달
      const response = await registerUser(user,profile);

       const joinMessage = await response.text();


      alert("회원가입 성공");
      // setUser({ ...user, isJoin: true });
      navigate("/login");
    } catch (err) {
      alert("회원가입 실패");
    }
  };

  // 공통 onChange 핸들러
  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({
      ...user,
      [name]: value
    });
  };

  return (
    <div>
      <form onSubmit={handleJoin}>
        <h3>회원가입</h3>
        <input
          type="text"
          placeholder="이메일"
          value={user.email}
          name="email"
          onChange={handleChange}
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={user.password}
          name="password"
          onChange={handleChange}
        />
        <input
          type="password"
          placeholder="비밀번호 확인"
          name="password_check"
          value={user.password_check}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="주소"
          name="address"
          value={user.address}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="휴대폰 번호"
          name="phone"
          value={user.phone}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="닉네임"
          name="nickname"
          value={user.nickname}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="유저 이름"
          name="username"
          value={user.username}
          onChange={handleChange}
        />
        <input
          type="file"
          name="profile"
          onChange={(e) =>
              setProfile(e.target.files[0])
          }
        />
        {/* <input
          type="text"
          name="role"
          onChange={handleChange}  
        /> */}


       <select
         name="role"
         value={user.role}
        onChange={handleChange}
>
       <option value="">역할 선택</option>
        <option value="USER">사용자</option>
           <option value="ADMIN">관리자</option>
        </select>


{/* <select name="role" value={user.role || "USER"} onChange={handleChange}>
  <option value="USER">사용자</option>
  <option value="ADMIN">관리자</option>
</select> */}



        <button type="submit">회원가입</button>
      </form>
    </div>
  );
};

export default Join;