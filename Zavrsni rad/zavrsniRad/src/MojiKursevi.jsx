import { useEffect, useState } from "react";
import KursKartica from "./KursKartica";

export default function MojiKursevi() {
  const [kursevi, setKursevi] = useState([]);
  useEffect(() => {
    const getData = async () => {
      const response = await fetch("http://localhost:8082/api/mojeUsluge", {
        credentials: "include",
      });
      let res = await response.json();
      if (res.res == "OK") {
        console.log(res)
        setKursevi(res["data"]);
      }
    };
    getData();
  }, []);
  return (
    <div className="listaKurseva">
      {kursevi.map((kurs, ind) => (
        <KursKartica
          key={ind}
          kursId={kurs["usl_id"]}
          oblastNaslov={kurs["obl_naziv"] + " / " + kurs["usl_naziv"]}
        />
      ))}
    </div>
  );
}
