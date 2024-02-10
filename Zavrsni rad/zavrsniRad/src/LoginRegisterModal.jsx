import { useLoginRegisterContext } from "./UserLogInContext"
import LoginForm from "./LoginForm"
import RegisterForm from "./RegisterForm"


function LoginRegisterModal(){
  const [state, setState] = useLoginRegisterContext()
  return(
    <>
      {state?.showLogin && <LoginForm/>}
      {state?.showReg && <RegisterForm/>}
    </>
  )
}
export default LoginRegisterModal