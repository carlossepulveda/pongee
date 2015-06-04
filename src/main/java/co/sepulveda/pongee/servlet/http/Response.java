package co.sepulveda.pongee.servlet.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Carlos Sepulveda
 */
public class Response {

    private final static String DEFAULT_CONTENT_TYPE = "application/json";
    private int status;
    private String contentType;
    private String body;
    private Object entity;
    private final List<Cookie> cookies;
    private final Map<String, String> headers;
    private String file;
    private String redirect;

    public Response() {
        status = 200;
        cookies = new ArrayList();
        headers = new HashMap();
    }

    public String getContentType() {
        if (contentType == null) {
            return DEFAULT_CONTENT_TYPE;
        }

        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Response withContentType(String contentType) {
        setContentType(contentType);
        return this;
    }

    public void setBody(Object body) {
        if (body instanceof String) {
            this.body = (String) body;
        } else {
            this.entity = body;
        }
    }

    public Response withBody(Object body) {
        setBody(body);
        return this;
    }

    public String getFormattedBody() {
        if (entity !=null && getContentType().contains("json")) {
            return toJSON(entity);
        }

        return body;
    }

    private String toJSON(Object entity) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Response withStatus(int status) {
        setStatus(status);
        return this;
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public Response withCookie(Cookie cookie) {
        addCookie(cookie);
        return this;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Response withHeader(String name, String value) {
        addHeader(name, value);
        return this;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Response withFile(String file) {
        setFile(file);
        return this;
    }

    public boolean isContentTypeSet() {
        return contentType != null;
    }

    public static Response redirect(String url) {
        Response response = new Response();
        response.redirect = url;

        return response;
    }

    public String getRedirect() {
        return redirect;
    }

    public static Response notFound() {
        Response response = new Response();
        response.setStatus(404);

        return response;
    }

    public static Response badRequest() {
        Response response = new Response();
        response.setStatus(400);

        return response;
    }

    public static Response unauthorized() {
        Response response = new Response();
        response.setStatus(401);

        return response;
    }

    public static Response internalError() {
        Response response = new Response();
        response.setStatus(500);

        return response;
    }
}
