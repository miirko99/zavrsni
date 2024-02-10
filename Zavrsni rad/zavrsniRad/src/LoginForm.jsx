import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLoginRegisterContext } from "./UserLogInContext";

function LoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [state, setState] = useLoginRegisterContext();
  const message = state.loginMsg;
  const setMessage = (msg) => {
    setState((prev) => 
      ({ ...prev, loginMsg: msg })
    );
  };
  const navigate = useNavigate();

  async function checkLogin() {
    try {
      const response = await fetch("http://localhost:8082/api/login", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
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
  function openRegister() {
    setState((p) => ({
      ...p,
      showLogin: false,
      showReg: true,
    }));
    setMessage("");
  }
  function close() {
    setState((p) => ({
      ...p,
      showLogin: false,
      showReg: false,
    }));
    setMessage("");
  }
  return (
    <div id="loginDiv">
      <h2>Login</h2>
      {message != "" && <h3 style={{ color: "red" }}>{message}</h3>}

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
      <div className="loginBtns">
        <button className="btn" onClick={checkLogin}>
          Login
        </button>
        <button className="btn" onClick={openRegister}>
          Register
        </button>
        <button className="btn" onClick={close}>
          Close
        </button>
      </div>
    </div>
  );
}

export default LoginForm;
