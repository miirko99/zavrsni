const Singup = {
     template: `<div>
        <h1>Singup</h1>
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
                         <td>Ime</td>
                         <td><input type="text" v-model="name"></td>
            </tr>
             <tr>
                          <td>Prezime</td>
                          <td><input type="text" v-model="lastname"></td>
                       </tr>
                        <tr>
                                     <td>Grad</td>
                                     <td><input type="text" v-model="city"></td>
                                  </tr>
                                   <tr>
                                                <td>Email</td>
                                                <td><input type="text" v-model="email"></td>
                                             </tr>
           <tr>
                <td></td>
                <td><button @click="singup()">prijava</button> </td>
           </tr>
        </table>
     </div>`,
     data() { return {
         username:"",
         password:"",
         name:"",
         lastname:"",
         city:"",
         email:""
     }},
     methods : {
         singup() {
             let hash = CryptoJS.SHA256(this.password).toString();
             post("/api/singup",{username:this.username,password:hash,name:this.name,lastname:this.lastname,city:this.city,email:this.email},
                (data) => {
                    app.setLogged(this.username);
                    router.push('/imenik');
                }
             )
         }
     }
}