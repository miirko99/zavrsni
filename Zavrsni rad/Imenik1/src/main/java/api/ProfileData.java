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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/myProfile")
public class ProfileData  extends MyHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        Boolean ok=false;
        JSONObject res=new JSONObject();
        HttpSession s = req.getSession(false);
        if(s == null){
            res.put("res","ERR");
            res.put("message","Login required");
            resp.setContentType("application/json");
            try(PrintWriter out=resp.getWriter()) {
                res.write(out);
            }
            return;
        }
        try {
            var q = Main.db.query("SELECT * FROM korisnik where kor_username=?", s.getAttribute("username"));

            res.put("userData",q.get(0));
            Map<String,Object> queryResult=q.get(0);
            Map<String,Object> userData = new HashMap<>();

            userData.put("name",queryResult.get("kor_ime"));
            userData.put("surname",queryResult.get("kor_prezime"));
            userData.put("password",queryResult.get("kor_sifra"));

            List<Map<String,Object>> gradoviDrzave=Main.db.query("select grad.gra_id,grad.gra_grad,drzava.drz_drzava from grad,drzava where grad.drz_id=drzava.drz_id");
            Map<String,List<String>> drzaveSaGradovima=new HashMap<>();
            for(Map<String,Object> gradDrzava:gradoviDrzave){
                if (drzaveSaGradovima.containsKey(gradDrzava.get("drz_drzava"))){
                    drzaveSaGradovima.get(gradDrzava.get("drz_drzava")).add((String) gradDrzava.get("gra_grad"));
                }
                else{
                    List<String> gradovi=new ArrayList<>();
                    gradovi.add((String) gradDrzava.get("gra_grad"));
                    drzaveSaGradovima.put((String) gradDrzava.get("drz_drzava"),gradovi);
                }
            }
            Long gradId = (Long) queryResult.get("gra_id");
            if ( gradId==null ){
                userData.put("grad","");
                userData.put("drzava","");
            }
            else{
                gradoviDrzave.forEach((gradDrzava)->{
                    if(gradDrzava.get("gra_id")==gradId){
                        userData.put("grad",gradDrzava.get("gra_grad"));
                        userData.put("drzava",gradDrzava.get("drz_drzava"));
                    }
                });
            }
            res.put("userData",userData);
            res.put("drzavaGradovi",drzaveSaGradovima);
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        JSONObject params=new JSONObject(new JSONTokener(req.getReader()));
        JSONObject res=new JSONObject();
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
            List<Map<String,Object>> grad = Main.db.query("select gra_id from grad where gra_grad=?",params.getString("grad"));
            List<Map<String,Object>> drzava = Main.db.query("select drz_id from drzava where drz_drzava=?",params.getString("drzava"));
            Long gradId;
            Long drzavaId;
            if(grad.isEmpty()){
                if(drzava.isEmpty()){
                    drzavaId = Long.valueOf(Main.db.execute("INSERT INTO drzava(drz_drzava) VALUES (?)",params.getString("drzava")).get(0));
                }
                else{
                    drzavaId = (Long) drzava.get(0).get("drz_id");
                }
                gradId = Long.valueOf(Main.db.execute("INSERT INTO grad(gra_grad, drz_id) VALUES (?,?)",params.getString("grad"),drzavaId).get(0));
            }
            else {
                gradId = (Long) grad.get(0).get("gra_id");
            }
            var e = Main.db.execute("UPDATE korisnik SET kor_ime = ?, kor_prezime = ?, kor_sifra = ?, gra_id = ? WHERE korisnik.kor_username = ?",params.getString("name"),params.getString("surname"),params.getString("password"),gradId,session.getAttribute("username"));
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
