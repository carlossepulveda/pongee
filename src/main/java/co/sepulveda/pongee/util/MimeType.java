package co.sepulveda.pongee.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Sepulveda
 */
public class MimeType {

    private static final Map<String, String> types;

    static {
        types = new HashMap<>();
        types.put("gif", "image/gif");
        types.put("jpeg", "image/jpeg");
        types.put("jpe", "image/jpeg");
        types.put("jpg", "image/jpeg");
        types.put("js", "application/x-javascript");
        types.put("pdf", "application/pdf");
        types.put("png", "image/png");
        types.put("html", "text/html");
        types.put("css", "text/css");
    }

    public static String getMiMeTypeFromFile(File file) {
        String ext = null;
        String[] fileInfo = file.getName().split("\\.");
        if (fileInfo.length > 1) {
            ext = fileInfo[fileInfo.length - 1];
        }

        String mimeType = types.get(ext);
        if (ext == null || mimeType == null) {
            return "text/plain";
        }

        return mimeType;
    }

}
