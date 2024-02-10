import { useEffect, useState } from "react";
import Kviz from "./Kviz";
import { useLoginRegisterContext } from "./UserLogInContext";

function DodajKurs() {
  const [loginState, setLoginState] = useLoginRegisterContext();
  const [formState, setFormState] = useState({
    naslov: "",
    opis: "",
  });
  const [oblast, setOblast] = useState("");
  const [kviz, setKviz] = useState(null);
  const [slika, setSlika] = useState(null);
  const [oblasti, setOblasti] = useState([]);
  const [prikaziKviz, setPrikaziKviz] = useState(false);
  
  useEffect(() => {
    const getData = async () => {
      try {
        let response = await fetch("http://localhost:8082/api/getOblasti", {
          credentials: "include",
        });
        let result = await response.json();
        setOblasti(result?.oblasti);
      } catch (e) {
        console.log(e);
      }
    };
    getData();
  }, []);

  function handleFormChange(e) {
    setFormState((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  }
  function handelSlikaChange(e) {
    setSlika(e.target.files[0]);
  }
  function handleOblastChange(e) {
    setOblast(e.target.value);
  }
  async function save() {
    try {
      const formData = new FormData();
      formData.append("file", slika);

      let jsonObject = {
        naslov: formState.naslov,
        opis: formState.opis,
        oblast: oblast,
        kviz: kviz,
      };
      let obj = JSON.stringify(jsonObject);
      formData.append("data", obj);
      const response = await fetch("http://localhost:8082/api/dodajKurs", {
        method: "POST",
        credentials: "include",
        body: formData,
      });
      let res = await response.json();
      if (res["res"] == "OK") {
        alert("Success")
      } else if (res["message"]=="login required") {
        setLoginState((prev) => ({
          ...prev,
          showLogin: true,
          loginMsg: "Login and save again.",
        }));
      }
    } catch (e) {
      console.log(e);
    }
  }
  return (
    <div id="dodajKursPage">
      <div id="kursForma">
        <input
          type="text"
          name="naslov"
          placeholder="naslov"
          onChange={handleFormChange}
          value={formState.naslov}
        />
        <select onChange={handleOblastChange}>
          {oblasti?.map((ob, i) => {
            return (
              <option key={i} value={ob}>
                {ob}
              </option>
            );
          })}
        </select>
        <textarea
          type="text"
          name="opis"
          rows="10"
          placeholder="opis"
          onChange={handleFormChange}
          value={formState.opis}
        />
        <input type="file" name="slika" onChange={handelSlikaChange} />
        <button className="btn" onClick={() => setPrikaziKviz(true)}>
          Dodaj Kviz
        </button>
        <button className="btn" onClick={save}>
          Save
        </button>
      </div>
      {prikaziKviz ? <Kviz setKviz={setKviz} /> : null}
    </div>
  );
}
export default DodajKurs;
