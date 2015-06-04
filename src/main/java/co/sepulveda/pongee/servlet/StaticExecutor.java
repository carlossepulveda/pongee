package co.sepulveda.pongee.servlet;

import co.sepulveda.pongee.util.MimeType;
import co.sepulveda.pongee.servlet.http.Response;
import co.sepulveda.pongee.servlet.render.Render;
import java.io.File;
import java.io.IOException;
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
        File file = getFileFromPath(pathFile);
        if (file == null || !file.exists()) {
            file = getFileFromCurrentThread(pathFile);
        }

        return file;
    }

    private File getFileFromPath(String pathFile) {
        try {
            return new File("static/" + pathFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getFileFromCurrentThread(String pathFile) {
        try {
            String path = "static/" + pathFile;
            String absolutePath = Thread.currentThread().getContextClassLoader().getResource(path).getFile();
            return new File(absolutePath);
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
