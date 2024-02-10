package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.Main;
import main.MyHttpServlet;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/login")
public class Login extends MyHttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");


        JSONObject params=new JSONObject(new JSONTokener(req.getReader()));
        String username=params.getString("username");
        String password=params.getString("password");
        boolean ok = false;
        JSONObject res=new JSONObject();
        try {
            var q = Main.db.query("SELECT kor_id FROM korisnik WHERE kor_username=? AND kor_sifra=?", username, password);
            ok = q.size()>0;
        } catch (SQLException e) {
            res.put("message",e.getMessage());
        }

        if(ok) {
            HttpSession s = req.getSession();
            s.setAttribute("username",username);
            Cookie c=new Cookie("JSESSIONID","907FCFAC888083D1331C5BE7E6");
            c.setHttpOnly(false);
            c.setSecure(false);
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
