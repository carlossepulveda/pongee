package co.sepulveda.pongee.servlet.render;

import co.sepulveda.pongee.servlet.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carlos Sepulveda
 */
public class Render {

    private PrintWriter responseWriter;

    public void render(HttpServletRequest request, HttpServletResponse response, Response responsePG) throws IOException {
        if (responsePG != null) {
            responseWriter = response.getWriter();
            render_(request, response, responsePG);
        }
    }

    private void render_(HttpServletRequest request, HttpServletResponse response, Response responsePG) {
        try {
            setHeaders(response, responsePG);
            setContentType(response, responsePG);
            setCookies(response, responsePG);
            setBody(response, responsePG);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    private void setHeaders(HttpServletResponse response, Response responsePG) {
        Map<String, String> headers = responsePG.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }
    }

    private void setContentType(HttpServletResponse response, Response responsePG) {
        response.setContentType(responsePG.getContentType());
    }

    private void setBody(HttpServletResponse response, Response responsePG) throws UnsupportedEncodingException {
        String body = responsePG.getBodyAsHTTPEntity();
        response.setContentLength(body.getBytes("UTF-8").length);
        responseWriter.write(body);
    }

    private void setCookies(HttpServletResponse response, Response responsePG) {
        for (Cookie cookie : responsePG.getCookies()) {
            response.addCookie(cookie);
        }
    }
}
