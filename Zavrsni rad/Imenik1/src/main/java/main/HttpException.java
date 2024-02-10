package main;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

public class HttpException extends Exception {
    int code;
    JSONObject obj=new JSONObject();

    public HttpException(int code, String message, Object...args) {
        super(String.format(message,args));
        this.code = code;
    }

    public HttpException(Exception e) {
        super(e.getMessage());
        if(e instanceof HttpException) {
            this.code=((HttpException)e).code;
        } else {
            this.code = 500;
        }
    }

    public HttpException put(String key, Object value) {
        obj.put(key,value);
        return this;
    }

    public JSONObject getJSON() {
        obj.put("res", "ERR");
        obj.put("code", code);
        obj.put("message", getMessage());
        return obj;
    }

    public void write(PrintWriter out) {
        JSONObject obj = getJSON();
        obj.write(out);
    }

    public void write(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            write(out);
        }
    }

}
