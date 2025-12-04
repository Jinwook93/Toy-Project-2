import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from '../pages/Login';
import Join from '../pages/Join';

const UserRouter = ({setUserName,setIsLogin}) => {
    return (
        <Routes>
        <Route path="/login" element={<Login setUserName={setUserName} setIsLogin={setIsLogin}/>} />
        <Route path="/join" element={<Join />} />
      </Routes>
  
    );
};

export default UserRouter;