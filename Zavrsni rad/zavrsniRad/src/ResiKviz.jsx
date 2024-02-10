import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function ResiKviz() {
  const { id } = useParams();
  const [pitanja, setPitanja] = useState([]);
  const [naziv, setNaziv] = useState("");
  const [index, setIndex] = useState(0);
  const [postoji, setPostoji] = useState(false);
  const [odgovori, setOdgovori] = useState([]);
  const [message, setMessage] = useState([]);
  useEffect(() => {
    getData();
  }, []);
  async function getData() {
    try {
      let response = await fetch("http://localhost:8082/api/kviz?id=" + id, {
        credentials: "include",
      });
      let result = await response.json();
      if (result["res"] == "OK") {
        setPostoji(true);
        setNaziv(result.kurs.kvi_naziv);
        setPitanja(JSON.parse(result.kurs.pitanja));
      }
    } catch (e) {
      console.log(e);
    }
  }
  function submitAnswer(ind) {
    if (index < pitanja.length) {
      if (odgovori.length == index) {
        odgovori.push(ind);
        setIndex((p) =>  (p + 1));
        if(index==pitanja.length-1){
          submitAnswers()
        }
      }
    }
  }
  async function submitAnswers() {
    console.log("submit");
    let response = await fetch("http://localhost:8082/api/kviz?id=" + id, {
      credentials: "include",
      method: "POST",
      body: JSON.stringify({ id: id, odgovori: odgovori }),
    });
    let result = await response.json();
    if (result["res"] == "OK") {
      console.log("asdf");
      setMessage(result["message"]);
    }
  }
  return (
    <>
      {postoji && index < pitanja.length && (
        <div>
          <h1>{naziv}</h1>
          <h2>Pitanje broj: {index + 1}</h2>
          <p>{pitanja[index]?.pitanje}</p>
          <div className="odgovori">
            {pitanja[index]?.odgovori.map((odg, ind) => (
              <button
                className="odgovor"
                onClick={() => {
                  submitAnswer(ind);
                }}
                key={ind}
              >
                {odg}
              </button>
            ))}
          </div>
        </div>
      )}
      {index == pitanja.length && <h2>{message}</h2>}
    </>
  );
}
