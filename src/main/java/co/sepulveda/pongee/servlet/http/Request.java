
package co.sepulveda.pongee.servlet.http;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Carlos Sepulveda
 */

public class Request{

    private final HttpServletRequest request;
    private String body;
    private List<FileItem> files = new ArrayList();
    private final Map<String, String> pathVars;

    public Request(HttpServletRequest request, Map<String, String> pathVars) {
        this.request = request;
        if (!ServletFileUpload.isMultipartContent(request)) {
            this.body = readBody(request);
        } else {
            this.files = readFiles(request);
        }

        this.pathVars = pathVars;
    }

    private String readBody(HttpServletRequest request) {
        try {
            Scanner s = new Scanner(request.getInputStream(), "UTF-8");
            return s.hasNext() ? s.next() : "";
        } catch (Exception e) { return null; }
    }

    public String getBodyAsString() {
        return body;
    }

    public <T> T parseBody(Class c) {
        if (body == null || body.isEmpty()) {
            return null;
        }

        try {
            String canonicalName = c.getCanonicalName();
            ObjectMapper mapper = new ObjectMapper();

            return (T) mapper.readValue(body, Class.forName(canonicalName));
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }

    private List<FileItem> readFiles(HttpServletRequest request) {
        try{
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024);
            factory.setRepository(new File("/tmp"));

            ServletFileUpload upload = new ServletFileUpload(factory);
            // max 8 MB
            long maxBytes = 8 * 1024 * 1024;
            upload.setSizeMax(maxBytes);

            List<FileItem> items = upload.parseRequest(request);

            return items;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<FileItem> getFiles() {
        return files;
    }

    public String getParameter(String parameter) {
        return request.getParameter(parameter);
    }

    public Cookie getCookie(String name) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }

        return null;
    }

    public String getPathVar(String name) {
        return pathVars.get(name);
    }
}
