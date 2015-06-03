package co.sepulveda.pongee.servlet;

import co.sepulveda.pongee.util.MimeType;
import co.sepulveda.pongee.servlet.http.Response;
import co.sepulveda.pongee.servlet.render.Render;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlossepulveda
 */
public class StaticExecutor {

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathFile = req.getPathInfo().replace("/static/", "");
        File file = getFile(pathFile);
        if (file != null && file.exists()) {
            renderFile(file, req, resp);
        } else {
            renderNotFound(req, resp);
        }
    }

    private File getFile(String pathFile) {
        try {
            URL urlFile = Thread.currentThread().getContextClassLoader().getResource("static/" + pathFile);
            return new File(urlFile.getFile());
        } catch (Exception e) {
            return null;
        }
    }

    private void renderFile(File file, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Response response = new Response();
        response.setFile(file.getPath());
        String contentType = MimeType.getMiMeTypeFromFile(file);
        response.setContentType(contentType);
        new Render().render(req, resp, response);
    }

    private void renderNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Response response = Response.notFound();
        new Render().render(req, resp, response);
    }
}
