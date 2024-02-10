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
import java.util.List;
import java.util.Map;

@WebServlet("/api/sveUsluge")
public class SveUsluge  extends MyHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        Boolean ok = false;
        JSONObject res = new JSONObject();
        try {
            List<Map<String,Object>> usluge = Main.db.query("select obl_naziv,usl_id,usl_naziv from usluga,oblast where usluga.obl_id=oblast.obl_id");

            res.put("data", usluge);
            ok = true;
        } catch (SQLException e) {
            res.put("message", e.getMessage());
        }

        if (ok) {
            res.put("res", "OK");
        } else {
            res.put("res", "ERR");
        }
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            res.write(out);
        }
    }
}
