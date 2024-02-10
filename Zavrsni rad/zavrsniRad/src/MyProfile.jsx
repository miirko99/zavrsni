import { useEffect, useState } from "react";
import { useLoginRegisterContext } from "./UserLogInContext";

export default function MyProfile() {
  const [appState, setAppState] = useLoginRegisterContext();
  const username = appState.userName;
  const [message, setMessage] = useState("");
  const [drzavaGradovi, setDrzavaGradovi] = useState({
    srbija: ["kragujevac", "beograd", "novi sad"],
    BIH: ["sarajevo", "mostar", "banjaluka"],
  });

  const [userData, setUserData] = useState({
    name: "",
    surname: "",
    password: "",
    grad: "-1",
    drzava: "-1",
  });
  useEffect(() => {
    const getData = async () => {
      const response = await fetch("http://localhost:8082/api/myProfile", {
        credentials: "include",
      });
      let res = await response.json();
      if (res.res == "OK") {
        setUserData(res.userData);
        setDrzavaGradovi(res.drzavaGradovi);
      }
    };
    getData();
  }, [username]);
  function handleChange(e) {
    setUserData((p) => ({ ...p, [e.target.name]: e.target.value }));
  }
  async function submitData() {
    const response = await fetch("http://localhost:8082/api/myProfile", {
      method: "POST",
      credentials: "include",
      body: JSON.stringify(userData),
    });
    let res = await response.json();
  }
  return (
    <div id="userDataForm">
      <h2 style={{ color: "red" }}>{message}</h2>
      <label htmlFor="name">Name:</label>
      <input
        id="name"
        name="name"
        placeholder="name"
        type="text"
        value={userData.name}
        onChange={handleChange}
      />
      <label htmlFor="surname">Surname:</label>
      <input
        id="surname"
        name="surname"
        placeholder="surname"
        type="text"
        value={userData.surname}
        onChange={handleChange}
      />
      <label htmlFor="password">Password:</label>
      <input
        id="password"
        name="password"
        placeholder="password"
        type="text"
        value={userData.password}
        onChange={handleChange}
      />
      <label htmlFor="choseDrzava">Drzava</label>
      <select
        id="choseDrzava"
        name="drzava"
        onChange={handleChange}
        value={
          Object.entries(drzavaGradovi).some(
            ([drzava, gradovi]) => drzava == userData.drzava
          )
            ? userData.drzava
            : "-1"
        }
      >
        {Object.entries(drzavaGradovi).map(([drzava, gradovi], ind) => (
          <option key={ind} value={drzava}>
            {drzava}
          </option>
        ))}
        <option value="-1">Neka druga drzava</option>
      </select>
      {!Object.entries(drzavaGradovi).some(
        ([drzava, gradovi]) => drzava == userData.drzava
      ) ? (
        <input
          name="drzava"
          placeholder="Nova drzava"
          onChange={handleChange}
        />
      ) : null}
      <label htmlFor="choseGrad">Grad</label>
      <select
        id="choseGrad"
        name="grad"
        onChange={handleChange}
        value={
          drzavaGradovi[userData.drzava]?.includes(userData.grad)
            ? userData.grad
            : "-1"
        }
      >
        {Object.entries(drzavaGradovi).map(([drzava, gradovi]) => {
          if (drzava == userData.drzava) {
            return gradovi.map((grad, ind) => (
              <option key={ind} value={grad}>
                {grad}
              </option>
            ));
          }
        })}
        <option value="-1">Neki drugi grad</option>
      </select>
      {!drzavaGradovi[userData.drzava]?.includes(userData.grad) ? (
        <input name="grad" placeholder="Nov grad" onChange={handleChange} />
      ) : null}
      <button className="btn" onClick={submitData}>
        Save
      </button>
    </div>
  );
}
