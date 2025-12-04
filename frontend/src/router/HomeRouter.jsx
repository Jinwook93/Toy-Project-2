import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from '../pages/Home';


const HomeRouter = (props) => {
    return (
      <Routes>
      <Route path="/" element={<Home isLogin={props.isLogin} />} />
    </Routes>

  
    );
};

export default HomeRouter;