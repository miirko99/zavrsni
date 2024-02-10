import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLoginRegisterContext } from "./UserLogInContext";

function RegisterForm() {
  const [message, setMessage] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();
  const [state, setState] = useLoginRegisterContext();
  let gUsername = state.userName;

  async function handleRegister() {
    try {
      const response = await fetch("http://localhost:8082/api/register", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: name,
          surname: surname,
          email: email,
          username: username,
          password: password,
        }),
      });
      let res = await response.json();
      if (res["res"] == "OK") {
        setState((p) => ({
          ...p,
          userName: username,
          showLogin: false,
          showReg: false,
        }));
        setMessage("");
      } else {
        setMessage("Bad credentials");
      }
    } catch (e) {
      console.log(e);
    }
  }

  function openLogin() {
    setState((p) => ({
      ...p,
      showLogin: true,
      showReg: false,
    }));
  }

  function close() {
    setState((p) => ({
      ...p,
      showLogin: false,
      showReg: false,
    }));
  }

  return (
    <div id="regDiv">
      <h2>Register</h2>
      {message != "" ? <h3 style={{ color: "red" }}>{message}</h3> : null}
      <input
        type="text"
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <input
        type="text"
        placeholder="Surname"
        value={surname}
        onChange={(e) => setSurname(e.target.value)}
      />
      <input
        type="text"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button className="btn" onClick={handleRegister}>
        Register
      </button>
      <button className="btn" onClick={openLogin}>
        Back to Login
      </button>
      <button className="btn" onClick={close}>
        Close
      </button>
    </div>
  );
}

export default RegisterForm;
