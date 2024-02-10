import Title from "./Title"
import logoImage from './assets/logo.png'
import GoToBtn from "./GoToBtn"
import DropDown from "./DropDown"
import LogedInButton from "./LogedInButton"

function NavBar(){
  return(
    <div id="navBar">
      <img src={logoImage} alt="" />
      <Title />
      <GoToBtn to="/" text="Home"/>
      <LogedInButton text="Moji kursevi" goTo="/mojiKursevi"/>
      <LogedInButton goTo="/dodajKurs" text="Dodaj kurs"/>
      <DropDown/>
    </div>
  )
}
export default NavBar