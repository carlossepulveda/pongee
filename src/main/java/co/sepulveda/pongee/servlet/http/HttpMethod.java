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

        if (method.toUpperCase().equals("GET")) return _GET;
        if (method.toUpperCase().equals("POST")) return _POST;
        if (method.toUpperCase().equals("PUT")) return _PUT;
        if (method.toUpperCase().equals("DELETE")) return _DELETE;

        return null;
    }
}
