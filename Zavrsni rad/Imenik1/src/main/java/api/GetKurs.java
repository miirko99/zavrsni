package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.Main;
import main.MyHttpServlet;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/getKurs")
public class GetKurs extends MyHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        Long id = Long.valueOf(req.getParameter("id"));

        Boolean ok=false;
        JSONObject res=new JSONObject();
        HttpSession s = req.getSession(false);
        if(s == null){
            res.put("res","ERR");
            resp.setContentType("application/json");
            try(PrintWriter out=resp.getWriter()) {
                res.write(out);
            }
            return;
        }

        try {
            Map<String ,Object> result = Main.db.query("select * from usluga,oblast where usl_id=? and usluga.obl_id=oblast.obl_id ",id).get(0);
            List<Map<String ,Object>> recenzije = Main.db.query("select * from recenzije,korisnik where recenzije.kor_id=korisnik.kor_id and usl_id=?",id);
            Double ocena = (Double) Main.db.query("select avg(rec_ocena) as a from recenzije where usl_id=?",id).get(0).get("a");
            var r=Main.db.query("select * from prijava,korisnik where korisnik.kor_id=prijava.kor_id and prijava.usl_id=? and korisnik.kor_username=?",id,s.getAttribute("username"));
            Boolean upisan=!r.isEmpty();
            res.put("uspisan",upisan);
            res.put("naziv",result.get("usl_naziv"));
            res.put("opis",result.get("usl_opis"));
            res.put("oblast",result.get("obl_naziv"));
            res.put("ocena",ocena);
            res.put("recenzije", recenzije);
            ok=true;
        } catch (SQLException e) {
            res.put("message",e.getMessage());
        }

        if(ok) {
            res.put("res","OK");
        } else {
            res.put("res","ERR");
        }
        resp.setContentType("application/json");
        try(PrintWriter out=resp.getWriter()) {
            res.write(out);
        }
    }
}
