const Login = {
     template: `<div>
        <h1>Login</h1>
        <table>
           <tr>
              <td>Korisnicko ime</td>
              <td><input type="text" v-model="username"></td>
           </tr>
           <tr>
               <td>Lozinka</td>
               <td><input type="password" v-model="password"></td>
           </tr>
           <tr>
                <td></td>
                <td><button @click="login()">prijava</button> </td>
           </tr>
        </table>
     </div>`,
     data() { return {
         username:"",
         password:""
     }},
     methods : {
         login() {
             let hash = CryptoJS.SHA256(this.password).toString();
             post("/api/login",{username:this.username,password:hash},
                (data) => {
                    app.setLogged(this.username);
                    router.push('/imenik');
                }
             )
         }
     }
}