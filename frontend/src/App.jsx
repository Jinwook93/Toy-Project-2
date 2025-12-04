import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { BrowserRouter } from 'react-router-dom'   // ✅ Router 대신 BrowserRouter
import UserRouter from './router/UserRouter'
import HomeRouter from './router/HomeRouter'
import Header from './components/Header'
import Footer from './components/Footer'

function App() {
  const [userName, setUserName] = useState("");   // 추가
  const [isLogin, setIsLogin] = useState(false);  // 추가
  const [id, setId] = useState(null);  //회원 식별번호


  return (
    <>
      <BrowserRouter>
      <Header  username={userName} isLogin={isLogin} id = {id}/>
        <UserRouter setUserName={setUserName} setIsLogin={setIsLogin} />
        <HomeRouter isLogin ={isLogin} id ={id}/>
        <Footer/> 
      </BrowserRouter>
    </>
  )
}

export default App