package co.sepulveda.pongee.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Sepulveda
 */
public class PathParser {

    public static boolean isRootResource(String resource, String url) {
        if (normalize(url).equals(normalize(resource))) {
            return true;
        }

        String[] urlData = normalize(url).split("/");
        resource = normalize(resource);

        return resource.equals(urlData[0]);
    }

    public static String[] getRegexPathAsArray(String path) {
        path = normalize(path);
        String[] pathData = path.split("/");
        for (int i = 0; i < pathData.length ; i++) {
            if (pathData[i].startsWith(":")) {
                pathData[i] = "::";
            }
        }

        return pathData;
    }

    public static boolean match(String url, String urlRegex) {
        Map data = getData(url, urlRegex);
        return data != null;
    }

    public static Map getData(String url, String urlRegex) {
        if (normalize(url).equals(normalize(urlRegex))) {
            return Collections.emptyMap();
        }

        String[] urlData = normalize(url).split("/");
        String[] urlRegexData = normalize(urlRegex).split("/");

        if (urlData.length != urlRegexData.length || urlData.length == 0 || urlRegexData.length == 0) {
            return null;
        }

        Map<String, String> data = new HashMap();
        for (int i = 0; i < urlData.length; i++) {
            String urlElement = urlData[i];
            String urlRegexElement = urlRegexData[i];
            if (!urlRegexElement.startsWith(":") && !urlRegexElement.equals(urlElement)) {
                return null;
            } else if (urlRegexElement.startsWith(":")) {
                String name = urlRegexElement.replace(":", "");
                data.put(name, urlElement);
            }
        }

        return data;
    }

    private static String normalize(String url) {
        if (url == null || url.length() == 1) {
            return "";
        }

        String aux = url;
        
        if (url.startsWith("/")) {
            aux = aux.substring(1, aux.length());
        }
        if (url.endsWith("/")) {
            aux = aux.substring(0, aux.length() - 1);
        }

        return aux;
    }

    public static void main(String[] args) {
        System.out.println(PathParser.getData("employees/3", "/employees/:id/"));
    }
}
