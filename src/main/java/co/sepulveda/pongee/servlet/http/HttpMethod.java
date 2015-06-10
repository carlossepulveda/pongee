package co.sepulveda.pongee.servlet.http;

/**
 *
 * @author Carlos Sepulveda
 */
public enum HttpMethod {

    _GET,
    _POST,
    _PUT,
    _DELETE,
    _HEAD,
    _OPTIONS,
    _TRACE;

    public static HttpMethod getMethod(String method) {
        if (method == null) return null;

        if (method.toUpperCase().equals("GET")) return _GET;
        if (method.toUpperCase().equals("POST")) return _POST;
        if (method.toUpperCase().equals("PUT")) return _PUT;
        if (method.toUpperCase().equals("DELETE")) return _DELETE;
        if (method.toUpperCase().equals("HEAD")) return _HEAD;
        if (method.toUpperCase().equals("OPTIONS")) return _OPTIONS;
        if (method.toUpperCase().equals("TRACE")) return _TRACE;

        return null;
    }
}
