import Home from "./Home";
import NavBar from "./NavBar";
import DodajKurs from "./DodajKurs";
import Kurs from "./Kurs";
import { Routes, Route } from "react-router-dom";
import LoginRegisterModal from "./LoginRegisterModal";
import { LoginRegisterProvider } from "./UserLogInContext";
import MyProfile from "./MyProfile";
import MojiKursevi from "./MojiKursevi";
import Kviz from "./Kviz";
import ResiKviz from "./ResiKviz";

function App() {
  return (
    <>
      <LoginRegisterProvider>
        <>
          <NavBar />
          <LoginRegisterModal />
          <Routes>
            <Route path="/home" element={<Home />} />
            <Route path="/" element={<Home />} />
            <Route path="/mojiKursevi" element={<MojiKursevi />} />
            <Route path="/dodajKurs" element={<DodajKurs />} />
            <Route path="/myProfile" element={<MyProfile />} />
            <Route path="/kurs/:id" element={<Kurs />} />
            <Route path="/kviz/:id" element={<ResiKviz />} />
          </Routes>
        </>
      </LoginRegisterProvider>
    </>
  );
}

export default App;
