package co.sepulveda.pongee.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carlos Sepulveda
 */
public class StaticExecutor {

    private static final String ERROR_404 = "404";

    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (!printFile(req.getPathInfo().substring(1), resp.getOutputStream())) {
                printErrorCode(resp.getOutputStream(), ERROR_404);
            }
        } catch (Exception e) {

        }
    }

    public static boolean printFile(String urlFile, ServletOutputStream os) {
        try {
            File file = new File(urlFile);
            FileInputStream input = new FileInputStream(file);
            FileChannel channel = input.getChannel();
            byte[] buffer = new byte[256 * 1024];
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
            try {
                for (int length = 0; (length = channel.read(byteBuffer)) != -1;) {
                    os.write(buffer, 0, length);
                    byteBuffer.clear();
                }
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                input.close();
            }
        } catch (Exception e) {
            return false;
        }

    }

    public static void printErrorCode(ServletOutputStream os, String code) {
        try {
            if (!printFile("errors/" + code + ".html", os)) {
                os.print("Error : " + code);
            }
        } catch (Exception e) {

        }
    }
}
