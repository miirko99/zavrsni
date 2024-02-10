import { useNavigate } from "react-router-dom"

function GoToBtn({to,text}){
  const navigate=useNavigate()
  return(
    <>
      <button className="btn" onClick={()=>{navigate(to)}}>{text}</button>
    </>
  )
}
export default GoToBtn