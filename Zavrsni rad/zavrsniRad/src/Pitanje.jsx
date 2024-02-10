export default function Pitanje({
  index,
  setIndex,
  pitanja,
  setPitanja,
}) {
  if (index >= pitanja.length) {
    pitanja.push({
      pitanje: "",
      tacan: null,
      odgovori: ["", "", "", ""],
    });
  }
  const pitanje = pitanja[index];

  function handleTextPitanjaChange(e) {
    let nPitanja = [...pitanja];
    let nPitanje = { ...pitanja[index] };
    nPitanje["pitanje"] = e.target.value;
    nPitanja[index] = nPitanje;

    setPitanja(nPitanja);
  }
  function handleOdgChange(e, odgInd) {
    let nPitanja = [...pitanja];
    let nPitanje = { ...pitanja[index] };
    let nOdgovori = [...nPitanje["odgovori"]];
    nOdgovori[odgInd] = e.target.value;
    nPitanje["odgovori"] = nOdgovori;
    nPitanja[index] = nPitanje;

    setPitanja(nPitanja);
  }
  function handleTacanChange(e, odgInd) {
    let nPitanja = [...pitanja];
    let nPitanje = { ...pitanja[index] };
    nPitanje["tacan"] = odgInd;
    nPitanja[index] = nPitanje;
    setPitanja(nPitanja);
  }
  function goPrevious() {
    setIndex((prev) => {
      if (prev == 0) {
        return prev;
      }
      return prev - 1;
    });
  }
  function removePitanje(){
    setPitanja(pitanja=>{
      let nPitanja = [...pitanja]
      nPitanja.splice(index,1)
      goPrevious()
      return nPitanja
    })
  }
  function goNext() {
    setIndex(prev=>prev+1)
  }
  return (
    <div className="pitanjeForma">
      <h4>Pitanje broj {index+1}</h4>
      <textarea
        name="pitanje"
        placeholder="Pitanje..."
        id="1"
        rows="5"
        style={{ width: "100%", padding: "0px" }}
        value={pitanje?.pitanje}
        onChange={(e) => {
          handleTextPitanjaChange(e);
        }}
      ></textarea>
      <div className="odgovori">
        {pitanje?.odgovori.map((odg, odgInd) => (
          <div key={pitanje.pitanje+odgInd}className="odgovor">            
            <input
              onChange={(e) => handleTacanChange(e, odgInd)}
              name="tacan"
              type="radio"
              id="0"
              value="0"
              checked={pitanje.tacan==odgInd}
            />
            <input
              onChange={(e) => handleOdgChange(e, odgInd)}
              type="text"
              placeholder="odgovor"
              value={odg}
            />
          </div>
        ))}
      </div>
      <div className="kvizButtons">
        <button className="btn" onClick={goPrevious}>
          Previous
        </button>
        <button className="btn" onClick={removePitanje}>
          Delete
        </button>
        <button className="btn" onClick={goNext}>
          Next
        </button>
      </div>
    </div>
  );
}
