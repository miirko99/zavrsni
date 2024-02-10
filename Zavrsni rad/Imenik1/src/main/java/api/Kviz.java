package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import main.Main;
import main.MyHttpServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/kviz")
public class Kviz extends MyHttpServlet {
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
            List<Map<String,Object>> kursevi = Main.db.query("select * from kviz where usl_id=?",id);
            if(kursevi.isEmpty()){
                res.put("message","nema kursa");
            }else {
                res.put("kurs",kursevi.get(0));
                ok=true;
            }
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        JSONObject params=new JSONObject(new JSONTokener(req.getReader()));
        Long id= params.getLong("id");
        List<Object> odgovori= params.getJSONArray("odgovori").toList();
        boolean ok = false;
        HttpSession session = req.getSession(false);
        JSONObject res=new JSONObject();
        if(session==null){
            res.put("res","ERR");
            res.put("message","login required");
            res.write(resp.getWriter());
            return;
        }

        try {
            Map<String,Object> kurs = Main.db.query("select * from kviz where usl_id=?",id).get(0);
            JSONArray tacniOdgovori = new JSONArray(kurs.get("pitanja").toString());
            Integer brojPoena=0;
            for(int i=0; i <  tacniOdgovori.length();i++){
                if ( odgovori.get(i) == new JSONObject(tacniOdgovori.get(i).toString()).get("tacan")){
                    brojPoena+=1;
                }
            }
            brojPoena=100/odgovori.size()*brojPoena;
            Long kvizId= (Long) kurs.get("kvi_id");
            Long korId = (Long) Main.db.query("select * from korisnik where kor_username=?",session.getAttribute("username")).get(0).get("kor_id");
            Main.db.execute("INSERT INTO rezultat (kor_id, kviz_id, date, points) VALUES (?, ?, NOW(), ?)",korId,kvizId,brojPoena);
            res.put("message", String.format("Osvojeno %d bodova", brojPoena));
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
