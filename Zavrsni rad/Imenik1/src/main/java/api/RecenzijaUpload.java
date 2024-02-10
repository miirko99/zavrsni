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

@WebServlet("/api/recenzija")
public class RecenzijaUpload extends MyHttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");


        JSONObject res=new JSONObject();
        JSONObject params=new JSONObject(new JSONTokener(req.getReader()));

        Long uslugaId=params.getLong("uslugaId");
        String recenzija=params.getString("recenzija");
        Integer ocena=params.getInt("ocena");
        boolean ok = false;
        HttpSession session = req.getSession(false);
        if(session==null){
            res.put("res","ERR");
            res.put("message","login required");
            resp.setContentType("application/json");
            res.write(resp.getWriter());
            return;
        }

        try {
            Long userid= (Long) Main.db.query("select kor_id from korisnik where kor_username=?",session.getAttribute("username")).get(0).get("kor_id");
            Main.db.execute("INSERT INTO recenzije (kor_id, usl_id, rec_recenzija, rec_ocena) VALUES (?, ?, ?, ?)",userid,uslugaId,recenzija,ocena);
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
