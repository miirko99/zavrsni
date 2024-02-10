package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/checklogin")
public class CheckLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject res=new JSONObject();
        HttpSession s=req.getSession(false);
        if(s==null) {
            res.put("res","ERR");
            res.put("message","No session");
        } else {
            res.put("res","OK");
            res.put("username",s.getAttribute("username"));
        }

        try(PrintWriter out=resp.getWriter()) {
            res.write(out);
        }
    }
}
