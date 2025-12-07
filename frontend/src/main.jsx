import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  // <StrictMode>
    <App />
  // </StrictMode>,


//   - StrictMode는 컴포넌트의 마운트 → 언마운트 → 다시 마운트 과정을 한 번 더 실행해서 사이드 이펙트(useEffect 등)를 검증합니다.
// - 그래서 useEffect 안의 fetchUserInfo()가 개발 모드에서 두 번 실행되는 거예요.
// - 실제 프로덕션 빌드에서는 한 번만 실행됩니다.

)
