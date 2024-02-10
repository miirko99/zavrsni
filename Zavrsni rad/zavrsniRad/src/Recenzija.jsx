export default function Recenzija({ text, username, ocena}){
  return <div className="recenzija">
  <p>{username+"   Ocena: "+ocena}</p>
  <p>{text}</p>
</div>
}