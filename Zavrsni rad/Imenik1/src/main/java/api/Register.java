package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.Main;
import main.MyHttpServlet;
import main.UserException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/register")
public class Register extends MyHttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        JSONObject params=new JSONObject(new JSONTokener(req.getReader()));
        String name = params.getString("name");
        String surname = params.getString("surname");
        String email = params.getString("email");
        String username = params.getString("username");
        String password = params.getString("password");

        JSONObject res = new JSONObject();
        boolean ok=false;
        try {
            var q = Main.db.query("SELECT kor_id FROM korisnik WHERE kor_username=? ", username);

            if(!q.isEmpty()){
                //ok = false;
                throw new UserException("username already taken");
            }
            Main.db.execute("INSERT INTO korisnik (kor_ime, kor_prezime, kor_email, kor_username, kor_sifra) VALUES (?, ?, ?, ?, ?);", name, surname, email, username, password);
            ok = true;

        } catch (SQLException | UserException e) {
            res.put("message",e.getMessage());
        }

        if(ok) {
            HttpSession s = req.getSession();
            s.setAttribute("username",username);
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
