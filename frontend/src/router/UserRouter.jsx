import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from '../pages/Login';
import Join from '../pages/Join';
import UpdateUser from '../pages/UpdateUser';
import GetLoginUserInfo from '../pages/GetLoginUserInfo';


const UserRouter = ({setUserName,setIsLogin, id, user, file}) => {
    return (
        <Routes>
        <Route path="/login" element={<Login setUserName={setUserName} setIsLogin={setIsLogin}/>} />
        <Route path="/join" element={<Join />} />
        <Route path="/getLoginUserInfo" element={<GetLoginUserInfo/>} />
         <Route path="/updateuser/:id" element={<UpdateUser />} /> 
      </Routes>
  
    );
};

export default UserRouter;