import React from "react"
import { useNavigate } from "react-router-dom"

function Title(){
  const navigate = useNavigate()
  return(
    <>
      <h2 onClick={()=>navigate("/")}>Kursevi</h2>
    </>
  )
}
export default Title