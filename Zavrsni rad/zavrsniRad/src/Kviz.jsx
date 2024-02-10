import { useEffect, useState } from "react";
import Pitanje from "./Pitanje";

export default function Kviz({ setKviz }) {
  const [pitanja, setPitanja] = useState([]);
  const [index, setIndex] = useState(0);
  const [naziv, setNaziv] = useState("");

  function handleNaslovChange(e) {
    setKviz((prev) => ({
      ...prev,
      naziv: e.target.value,
    }));
  }
  useEffect(() => {
    setKviz((prev) => ({
      ...prev,
      naziv: naziv,
      pitanja: pitanja,
    }));
  }, [pitanja]);

  return (
    <div id="kvizForma">
      <input
        type="text"
        placeholder="Naziv kviza"
        onChange={(e) => {
          handleNaslovChange(e);
        }}
        value={Kviz.naziv}
      />
      {
        <Pitanje
          index={index}
          setIndex={setIndex}
          setPitanja={setPitanja}
          pitanja={pitanja}
        />
      }
    </div>
  );
}
