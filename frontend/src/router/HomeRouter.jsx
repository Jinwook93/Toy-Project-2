import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from '../pages/Home';
import OAuth2Redirect from '../pages/oAuth2Redirect';


const HomeRouter = () => {
  return (
    <Routes>
    <Route path="/" element={<Home/>} />
    <Route path="/oauth2/redirect" element={<OAuth2Redirect/>} />   {/*oauth2 리다이렉트}*/}
  </Routes>


  );
};

export default HomeRouter;






// const HomeRouter = (props) => {
//     return (
//       <Routes>
//       <Route path="/" element={<Home id = {props.id} isLogin={props.isLogin} />} />
//     </Routes>

  
//     );
// };

// export default HomeRouter;