import { useLoginRegisterContext } from "./UserLogInContext"
import GoToBtn from "./GoToBtn"

export default function LogedInButton({text, goTo, withUsername}){
  
  const [state, setState] = useLoginRegisterContext()
  let username = state?.userName
  return (<>
      {username!=null? <GoToBtn to={goTo+(withUsername?username:"")} text={text}/>:null}
    </>
  )
}