package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.Main;
import main.MyHttpServlet;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/getOblasti")
public class GetOblasti extends MyHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
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
            var q = Main.db.query("SELECT * FROM oblast");
            List<String> oblasti= new ArrayList<>();
            q.forEach(e->{oblasti.add((String) e.get("obl_naziv"));});
            res.put("oblasti",oblasti);
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
