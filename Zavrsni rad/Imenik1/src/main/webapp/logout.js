const Logout = {
   template: '<div>Logout</div>',
   mounted() {
        get("/api/logout",(data)=>{
            app.setLoggedOut();
            router.push("/");

        })
   }
}