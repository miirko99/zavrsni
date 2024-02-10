import { useNavigate, useParams } from "react-router-dom";
import Recenzija from "./Recenzija";
import { useEffect, useState } from "react";

function Kurs() {
  const { id } = useParams();
  const [recenzije, setRecenzije] = useState([]);
  const [kursData, setKursData] = useState({});
  const [inputRecenzija, setInputRecenzija] = useState({ show: false });
  
  const navigate=useNavigate()
  
  useEffect(() => {
    getData();
  }, []);
  async function getData() {
    try {
      let response = await fetch("http://localhost:8082/api/getKurs?id=" + id, {
        credentials: "include",
      });
      let result = await response.json();
      if (result["res"] == "OK") {
        setKursData((prev) => ({
          naziv: result["naziv"],
          oblast: result["oblast"],
          ocena: result["ocena"],
          opis: result["opis"],
          upisan: result["uspisan"]
        }));
        setRecenzije(result["recenzije"]);
      }
    } catch (e) {
      console.log(e);
    }
  }
  async function upisiKurs(){
    try {
      let response = await fetch("http://localhost:8082/api/upisiKurs", {
        credentials: "include",
        method:"POST",
        body:JSON.stringify({id:id})
      });
      let result = await response.json();
      if (result["res"] == "OK") {
        setKursData(p=>({...p,upisan:true}))
      }
    } catch (e) {
      console.log(e);
    }
  }
  async function saveRecenzija() {
    try {
      let response = await fetch("http://localhost:8082/api/recenzija", {
        credentials: "include",
        method: "POST",
        body: JSON.stringify({
          uslugaId: id,
          ocena: inputRecenzija.ocena,
          recenzija: inputRecenzija.recenzija,
        }),
      });
      let result = await response.json();
      if (result["res"] == "OK") {
        getData();
        setInputRecenzija({ show: false });
      }
    } catch (e) {
      console.log(e);
    }
  }
  function handleInputChange(e) {
    setInputRecenzija((p) => ({
      ...p,
      [e.target.name]: e.target.value,
    }));
  }
  return (
    <div className="kursPage">
      <div className="kursInfo">
        <div className="slikaButtons">
          <img src={"http://localhost:8082/api/slikaprikaz?id=" + id} alt="" />
          <div className="buttons">
            {kursData.upisan?"Upisan":<button className="btn" onClick={upisiKurs}>
              Upisi
            </button>}
            <button
              className="btn"
              onClick={() => {
                setInputRecenzija({ show: true });
              }}
            >
              Recenzija
            </button>
            <button className="btn" onClick={() => {navigate("/kviz/"+id)}}>
              kviz
            </button>
          </div>
        </div>
        <div className="naslovOpis">
          <p>{kursData["oblast"] + " / " + kursData["naziv"]}</p>
          <p>{"Prosecna ocena: " + kursData["ocena"]}</p>
          <p>{kursData["opis"]}</p>
        </div>
      </div>
      {inputRecenzija.show && (
        <div className="inputRecenzija">
          <label htmlFor="recenzijaInput">Recenzija:</label>
          <textarea
            id="recenzijaInput"
            onChange={handleInputChange}
            name="recenzija"
            rows="5"
            placeholder="Recenzija"
            value={inputRecenzija.recenzija}
          ></textarea>
          <label htmlFor="ocena">Ocena:</label>
          <input
            onChange={handleInputChange}
            name="ocena"
            value={inputRecenzija.ocena}
            id="ocena"
            type="number"
            max={10}
            min={1}
            step={1}
          />
          <button className="btn" onClick={saveRecenzija}>
            Save
          </button>
        </div>
      )}
      <div className="recenzije">
        <h2>Recenzije</h2>
        {recenzije.map((rec, ind) => (
          <Recenzija
            key={ind}
            text={rec["rec_recenzija"]}
            username={rec["kor_username"]}
            ocena={rec["rec_ocena"]}
          />
        ))}
      </div>
    </div>
  );
}
export default Kurs;
