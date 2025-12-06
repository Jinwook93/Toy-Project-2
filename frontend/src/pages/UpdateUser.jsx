import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getLoginUserInfo, updateUser } from "../api/userAPI";
import { useDispatch } from "react-redux";
import { setIsLogin } from "../redux/userAction";
import { isDuplicateEmail, isDuplicateNickname } from "../api/userAPI";
import "../css/updateform.css";





const UpdateUser = () => {
  const [userInfo, setUserInfo] =  useState(null);
  const [profile, setProfile] = useState(null);
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



  // const [profile, setProfile] = useState("");
  const navigate = useNavigate();
  //const { id } = useParams(); // URL에서 :id 값 가져오기

  const handleUpdate = async (e) => {
    e.preventDefault();

    // if (userInfo.password !== userInfo.password_check) {
    //   alert("비밀번호와 확인값이 일치하지 않습니다.");
    //   return;
    // }

    if(e.target.email.value === ""){
      alert("이메일을 입력하세요");
    return;
    }

    if(e.target.password.value === "" || e.target.password_check.value === ""){
      if(e.target.password.value === ""){
      alert("비밀번호를 입력해주세요");
      }else{
        alert("비밀번호를 확인해주세요");
      }
      return;
    }



    if(e.target.password.value !== e.target.password_check.value){
      alert("비밀번호와 확인값이 같지 않습니다.")
      return;
    }


    if(await isDuplicateEmail(e.target.email.value, true)== true){
      // alert("중복된 이메일입니다.")
      return;
    }

    if(await isDuplicateNickname(e.target.nickname.value, true)== true){
      // alert("중복된 이메일입니다.")
      return;
    }



    try {
    
  
      const response = await updateUser(userInfo, profile);
    //  const updateMessage = await response.text();

      if (response.ok) {
        alert("유저 정보 수정 성공"); // "유저 정보 수정 성공"
     //   props.setIsLogin(false);
        dispatch(setIsLogin(false));
        localStorage.removeItem("jwt");
        localStorage.removeItem("refreshJwt");
        alert("수정된 정보로 다시 로그인해주세요"); 
        navigate("/");
      } else {
        alert("유저 정보 수정 실패"); // "유저 정보 수정 실패"
      }
    } catch (err) {
      alert("회원수정 실패 (네트워크 오류)");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserInfo({
      ...userInfo,
      [name]: value,
    });
  };

  return (
    <div className = "update-container">
       {userInfo ? (
      <form onSubmit={handleUpdate}>
        <h3>회원정보 수정</h3>
        <div className ="row">
        <input type="text" placeholder="이메일" value={userInfo.email} name="email" onChange={handleChange} />
        <button type="button" onClick={()=>{isDuplicateEmail(userInfo.email, false)}}>
        중복확인
        </button>
        </div>
        <div className ="row">
        <input type="text" placeholder="닉네임" value={userInfo.nickname} name="nickname" onChange={handleChange} />
        <button type="button" onClick={()=>{isDuplicateNickname(userInfo.nickname, false)}}>
        중복확인
        </button>
        </div>
        <input type="password" placeholder="비밀번호" value={userInfo.password} name="password" onChange={handleChange} />
        <input type="password" placeholder="비밀번호 확인" value={userInfo.password_check} name="password_check" onChange={handleChange} />
        <input type="text" placeholder="주소" value={userInfo.address} name="address" onChange={handleChange} />
        <input type="text" placeholder="휴대폰 번호" value={userInfo.phone} name="phone" onChange={handleChange} />
       
        <input type="text" placeholder="유저 이름" value={userInfo.username} name="username" onChange={handleChange} />
        <input type="file" name="profile" onChange={(e) => setProfile(e.target.files[0])} />
        <p>프로필: <img 
             src={`data:image/png;base64,${userInfo.profile}`} 
            alt="프로필 이미지" 
            /></p>
        
        <select name="role" value={userInfo.role} onChange={handleChange}>
          <option value="">역할 선택</option>
          <option value="USER">사용자</option>
          <option value="ADMIN">관리자</option>
        </select>
        <button type="submit">회원수정</button>
      </form>
       ):(
        <p>로딩 중...</p>
      )}
    </div>
  );
};

export default UpdateUser;










// const UpdateUser = (props) => {
//   const [userInfo, setUserInfo] =  useState(null);
//   const [profile, setProfile] = useState(null);




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



//   // const [profile, setProfile] = useState("");
//   const navigate = useNavigate();
//   //const { id } = useParams(); // URL에서 :id 값 가져오기

//   const handleUpdate = async (e) => {
//     e.preventDefault();

//     // if (userInfo.password !== userInfo.password_check) {
//     //   alert("비밀번호와 확인값이 일치하지 않습니다.");
//     //   return;
//     // }

//     try {
    
  
//       const response = await updateUser(userInfo, profile);
//     //  const updateMessage = await response.text();

//       if (response.ok) {
//         alert("유저 정보 수정 성공"); // "유저 정보 수정 성공"
//         props.setIsLogin(false);
//         localStorage.removeItem("jwt");
//         localStorage.removeItem("refreshJwt");
//         navigate("/");
//       } else {
//         alert("유저 정보 수정 실패"); // "유저 정보 수정 실패"
//       }
//     } catch (err) {
//       alert("회원수정 실패 (네트워크 오류)");
//     }
//   };

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setUserInfo({
//       ...userInfo,
//       [name]: value,
//     });
//   };

//   return (
//     <div>
//        {userInfo ? (
//       <form onSubmit={handleUpdate}>
//         <h3>회원정보 수정</h3>
//         <input type="text" placeholder="이메일" value={userInfo.email} name="email" onChange={handleChange} />
//         <input type="password" placeholder="비밀번호" value={userInfo.password} name="password" onChange={handleChange} />
//         <input type="password" placeholder="비밀번호 확인" value={userInfo.password_check} name="password_check" onChange={handleChange} />
//         <input type="text" placeholder="주소" value={userInfo.address} name="address" onChange={handleChange} />
//         <input type="text" placeholder="휴대폰 번호" value={userInfo.phone} name="phone" onChange={handleChange} />
//         <input type="text" placeholder="닉네임" value={userInfo.nickname} name="nickname" onChange={handleChange} />
//         <input type="text" placeholder="유저 이름" value={userInfo.username} name="username" onChange={handleChange} />
//         <input type="file" name="profile" onChange={(e) => setProfile(e.target.files[0])} />
//         <p>프로필: <img 
//              src={`data:image/png;base64,${userInfo.profile}`} 
//             alt="프로필 이미지" 
//             /></p>
        
//         <select name="role" value={userInfo.role} onChange={handleChange}>
//           <option value="">역할 선택</option>
//           <option value="USER">사용자</option>
//           <option value="ADMIN">관리자</option>
//         </select>
//         <button type="submit">회원수정</button>
//       </form>
//        ):(
//         <p>로딩 중...</p>
//       )}
//     </div>
//   );
// };

// export default UpdateUser;