import { createContext, useContext, useState } from "react";

const LoginRegisterContext = createContext();

export const useLoginRegisterContext = () => {
  return useContext(LoginRegisterContext);
};

export const LoginRegisterProvider = ({ children }) => {
  const [state, setState] = useState({
    userName: null,
    showLogin: false,
    loginMsg: "",
    showReg: false,
  });
  return (
    <LoginRegisterContext.Provider value={[state, setState]}>
      {children}
    </LoginRegisterContext.Provider>
  );
};
