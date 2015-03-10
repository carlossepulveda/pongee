package co.sepulveda.pongee.servlet;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;

/**
 *
 * @author Carlos Sepulveda
 */
public class Response {

    private String contentType;
    private String body;
    private Object entity;
    private final List<Cookie> cookies;
    private final Map<String, String> headers;

    public Response() {
        contentType = "application/json";
        body = "";
        cookies = new ArrayList();
        headers = new HashMap();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyAsHTTPEntity() {
        if (body != null && !body.isEmpty()) {
            return body;
        }

        return formatEntity();
    }

    private String formatEntity() {
        if (contentType.contains("json")) {
            Gson gson = new Gson();
            return gson.toJson(entity);
        }

        return body;
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
