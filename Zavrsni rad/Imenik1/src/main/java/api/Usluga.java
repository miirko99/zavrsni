package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.Main;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/usluga")
public class Usluga extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject params=new JSONObject(new JSONTokener(req.getReader()));
        String naziv=params.getString("naame");
        String opis=params.getString("desc");
        String oblast=params.getString("category");
        JSONObject res=new JSONObject();
        try {
            var q = Main.db.query("SELECT obl_id FROM oblast WHERE obl_naziv=? ", oblast);
            if (q.size()==0){
                Main.db.execute("INSERT INTO oblast (obl_naziv) VALUES (?)",oblast);
                q = Main.db.query("SELECT obl_id FROM oblast WHERE obl_naziv=? ", oblast);
            }
            Main.db.execute("INSERT INTO usluga (obl_id,usl_naziv,usl_opis) VALUES (?,?,?)",q.get(0).get("obl_id"),naziv,opis);
            var usl_id = Main.db.query("SELECT usl_id FROM usluga WHERE usl_naziv=? ", naziv).get(0).get("usl_id");
            res.put("data","uslugaId="+usl_id);
            res.put("res","OK");

        } catch (SQLException e) {
            res.put("message",e.getMessage());
            res.put("res","ERR");
        }


        try(PrintWriter out=resp.getWriter()) {
            res.write(out);
        }
    }
}
