package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.HttpException;
import main.Main;
import main.MyHttpServlet;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/uslugaupload")
@MultipartConfig(maxRequestSize = 25_000_000)
public class UslugaUpload extends MyHttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        try {
            String partName="newFile";
            Part part=req.getPart(partName);
            Part usl_id=req.getPart("uslugaID");
            if(part==null)
                throw new HttpException(400,"Parameter '%s' is required",partName);
            byte[] data=part.getInputStream().readAllBytes();
            JSONObject res=new JSONObject();
            Main.db.execute("UPDATE usluga set usl_slika=? WHERE usl_id=?",data,usl_id);
            res.put("res","OK");
            try(PrintWriter out=resp.getWriter()) {
                res.write(out);
            }
        } catch(Exception e) {
            e.printStackTrace();
            HttpException h = new HttpException(e);
            h.write(resp);
        }

        /*HttpSession s=req.getSession(false);
        if(s!=null)
        */



    }
}
