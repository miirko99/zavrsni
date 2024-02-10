const Slike = {
    template: `<div>
     <table>
               <tr>
                  <td>Naziv</td>
                  <td><input type="text" v-model="name"></td>
               </tr>
               <tr>
                   <td>Opis</td>
                   <td><input type="text" v-model="desc"></td>
               </tr>
                <tr>
                             <td>Oblast</td>
                             <td><input type="text" v-model="category"></td>
                </tr>

                                       <tr>
                                                    <td><input type="file" ref="fileInput" @change="prepareFile()"/></td>
                                                    <td><input type="text" v-model="email"></td>
                                                 </tr>
               <tr>
                    <td></td>
                    <td><button @click="submit()">Upload</button></td>
               </tr>
            </table>


        <br>
       <img height=200 v-for="img in images" :src="'/api/slikaprikaz?id='+img.sli_id">

    </div>`,
    data: function() { return {
        newFile: null,
        images: [],
        name:"",
        desc:"",
        category:""
    }},
    methods: {
        getImages() {
            get('/api/slike', (data)=>{
               this.images=data.data;
            })
        },
        submit() {
                     post("/api/usluga",{name:this.name,desc:this.dec,category:this.category},

               (data)=>{

            postMP( '/api/uslugaupload', { newFile: this.newFile,uslugaID:data.uslugaId}, () => {this.getImages()})
            })
        },
        prepareFile() {
            this.newFile = this.$refs.fileInput.files[0];
        }
    },
    mounted() {
        this.getImages();
    }
}