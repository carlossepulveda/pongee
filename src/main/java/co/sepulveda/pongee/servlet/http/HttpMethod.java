package co.sepulveda.pongee.servlet.http;

/**
 *
 * @author Carlos Sepulveda
 */
public enum HttpMethod {

    _GET,
    _POST,
    _PUT,
    _DELETE;

    public static HttpMethod getMethod(String method) {
        if (method == null) return null;

        if (method.equals("GET")) return _GET;
        if (method.equals("POST")) return _POST;
        if (method.equals("PUT")) return _PUT;
        if (method.equals("DELETE")) return _DELETE;

        return null;
    }
}
