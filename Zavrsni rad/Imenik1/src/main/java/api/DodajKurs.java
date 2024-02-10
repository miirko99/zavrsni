package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import main.Main;
import main.MyHttpServlet;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/dodajKurs")
@MultipartConfig(maxRequestSize = 25_000_000)
public class DodajKurs extends MyHttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        Part slika=req.getPart("file");
        String jsonData = req.getParameter("data");

        boolean ok = false;
        HttpSession session = req.getSession(false);
        JSONObject data=new JSONObject(jsonData);
        JSONObject res=new JSONObject();
        if(session==null){
            res.put("res","ERR");
            res.put("message","login required");
            res.write(resp.getWriter());
            return;
        }

        try {
            List<Map<String,Object>> oblast = Main.db.query("SELECT obl_id FROM oblast where obl_naziv=?",data.get("oblast"));
            Long oblast_id = null;
            if (!oblast.isEmpty()){
                oblast_id = (Long) oblast.get(0).get("obl_id");
            }
            List<Integer> usl_keys = Main.db.execute("INSERT INTO usluga (`obl_id`, `usl_naziv`, `usl_opis`, `usl_slika`) VALUES ( ?, ?, ?, ?)",oblast_id , data.get("naslov"), data.get("opis"), slika.getInputStream().readAllBytes());

            if(!data.isNull("kviz")){
                Main.db.execute("INSERT INTO kviz (usl_id, kvi_naziv, kvi_datum, pitanja) VALUES ( ?, ?, NOW() , ?)", usl_keys.get(0), data.getJSONObject("kviz").get("naziv"), data.getJSONObject("kviz").get("pitanja").toString());
            }
            List<Map<String,Object>> users = Main.db.query("select kor_id from korisnik where kor_username=?",session.getAttribute("username"));
            Main.db.execute("insert into vlasnik values(?,?)", users.get(0).get("kor_id"), usl_keys.get(0));
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

