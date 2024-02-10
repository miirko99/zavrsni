import { useNavigate } from "react-router-dom"

function KursKartica({kursId,oblastNaslov}){
  const navigate=useNavigate()
  return (
  <div className="kursWrapper" onClick={()=>{navigate("/kurs/"+kursId)}}>
    <div className="kurs">
      <img src={"http://localhost:8082/api/slikaprikaz?id="+kursId} alt="slika" />
      <p>{oblastNaslov}</p>
    </div>
  </div>)
}
export default KursKartica