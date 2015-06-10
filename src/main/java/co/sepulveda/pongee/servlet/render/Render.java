package co.sepulveda.pongee.servlet.render;

import co.sepulveda.pongee.servlet.http.Response;
import co.sepulveda.pongee.util.MimeType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carlos Sepulveda
 */
public class Render {

    private ServletOutputStream responseWriter;

    public void render(HttpServletRequest request, HttpServletResponse response, Response responsePG) throws IOException {
        if (responsePG != null) {
            responseWriter = response.getOutputStream();
            render_(request, response, responsePG);
        }
    }

    private void render_(HttpServletRequest request, HttpServletResponse response, Response responsePG) {
        try {
            if (responsePG.getRedirect() != null) {
                response.sendRedirect(responsePG.getRedirect());
                return;
            }

            setStatus(response, responsePG);
            setHeaders(response, responsePG);
            setContentType(response, responsePG);
            setCookies(request, response, responsePG);
            setBody(response, responsePG);
        } catch (Exception e) {
            renderInternalError(response);
        }
    }

    private void setStatus(HttpServletResponse response, Response responsePG) {
        response.setStatus(responsePG.getStatus());
    }

    private void setHeaders(HttpServletResponse response, Response responsePG) {
        Map<String, String> headers = responsePG.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }
    }

    private void setContentType(HttpServletResponse response, Response responsePG) {
        String contentType = responsePG.getContentType();
        if (hasToUseFileMimeType(responsePG)) {
            File file = new File(responsePG.getFile());
            contentType = MimeType.getMiMeTypeFromFile(file);
        }

        response.setContentType(contentType);
    }

    private boolean hasToUseFileMimeType(Response response) {
        return response.getFile() != null 
                && !response.getFile().isEmpty()
                && !response.isContentTypeSet();
    }

    private void setBody(HttpServletResponse response, Response responsePG) throws UnsupportedEncodingException, IOException {
        if (responsePG.getFile() != null) {
            String filePath = responsePG.getFile();
            renderFile(response, filePath);
            return;
        }

        String body = responsePG.getFormattedBody();
        if (body != null) {
            response.setContentLength(body.getBytes("UTF-8").length);
            responseWriter.print(body);
        }
    }

    private void setCookies(HttpServletRequest request, HttpServletResponse response, Response responsePG) {
        for (Cookie cookie : responsePG.getCookies()) {
            response.addCookie(cookie);
        }

        for (Cookie cookie : request.getCookies()) {
            response.addCookie(cookie);
        }
    }

    private void renderFile(HttpServletResponse response, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            renderInternalError(response);
            return;
        }

        try {
            renderFile(file);
        } catch (Exception e) {
            renderInternalError(response);
        }
    }

    private void renderFile(File file) throws IOException {
        responseWriter.flush();
        FileInputStream input = new FileInputStream(file);
        FileChannel channel = input.getChannel();
        byte[] buffer = new byte[256 * 1024];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        for (int length = 0; (length = channel.read(byteBuffer)) != -1;) {
            responseWriter.write(buffer, 0, length);
            byteBuffer.clear();
        }

        input.close();
    }

    private void renderInternalError(HttpServletResponse response) {
        response.setStatus(500);
        try {
            responseWriter.flush();
            responseWriter.print("Internal Error");
        } catch (Exception e) {
        }
    }
}
