const Imenik= {
     template: `<div>
         <h1>Imenik</h1>
         <table>
            <tr v-for="row in imenik">
               <td>{{row.imk_ime}}</td>
               <td>{{row.imk_prezime}}</td>
               <td>{{row.imk_dtrodj}}</td>
               <td>{{row.imk_telefon}}</td>
               <td>{{row.imk_beleske}}</td>
            </tr>
         </table>
     </div>`,
     data() { return {
         imenik:[]
     }},
     methods: {
         getImenik() {
             get("/api/imenik",(data) => {
                 this.imenik=data.data;
             });
         }

     },
     mounted() {
         this.getImenik();
     }

}