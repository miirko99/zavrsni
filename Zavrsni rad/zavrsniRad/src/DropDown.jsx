import { useState } from "react";
import GoToBtn from "./GoToBtn";
import { useLoginRegisterContext } from "./UserLogInContext";

function DropDown() {
  const [opened, setOpened] = useState(false);
  const [state, setState] = useLoginRegisterContext();

  let username = state?.userName;

  function togleOpened() {
    setOpened(!opened);
  }
  async function logout() {
    try {
      const response = await fetch("http://localhost:8082/api/logout", {
        credentials: "include",
      });
      let res = await response.json();
      if (res["res"] == "OK") {
        setState((p) => ({
          ...p,
          userName: null,
          showLogin: false,
          showReg: false,
        }));
      } else {
        alert("logout failed");
      }
    } catch (e) {
      console.log(e);
    }
  }

  function showLogin() {
    setState((prev) => ({
      ...prev,
      showLogin: true,
      showReg: false,
    }));
    setOpened(false);
  }

  return username ? (
    <div className="dropDownDiv">
      <button className="btn" onClick={togleOpened}>
        {username} &#9662;
      </button>
      {opened && (
        <div>
          <GoToBtn to={"/myProfile"} text="My profile" />
          <button className="btn" onClick={logout}>
            Logout
          </button>
        </div>
      )}
    </div>
  ) : (
    <button className="btn" onClick={showLogin}>
      Log in
    </button>
  );
}
export default DropDown;
