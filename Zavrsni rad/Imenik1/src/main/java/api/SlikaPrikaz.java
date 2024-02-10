package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.HttpException;
import main.Main;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/api/slikaprikaz")
public class SlikaPrikaz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = 0;
            try {
                id = Integer.parseInt(req.getParameter("id"));
            } catch (Exception e) {
                throw new HttpException(400, "id is required parameter type integer");
            }

            var q = Main.db.query("SELECT usl_slika FROM usluga WHERE usl_id=?", id);
            if (q.isEmpty()) {
                throw new HttpException(400, "there is no image with id=%d",id);
            }
            byte[] data = (byte[]) q.get(0).get("usl_slika");
            try (OutputStream out = resp.getOutputStream()) {
                out.write(data);
            }

        } catch (Exception e) {
            HttpException h = new HttpException(e);
            h.write(resp);
        }

    }
}
