
package pgServer;

import javax.servlet.http.HttpServletRequest;
import pgServer.util.URLMatcher;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */

public class HttpRequestPG{

    private HttpServletRequest request;
    private URLMatcher urlMatcher;

    public HttpRequestPG(HttpServletRequest request) {
        this.request = request;
    }

    public void createQueryParams(String urlQueryParams) {
        this.urlMatcher = new URLMatcher( urlQueryParams , request.getPathInfo() );
    }

    public String getURLParam(String key) {
        return urlMatcher.getValue(key);
    }

    public HttpServletRequest getHttpServletRequest() {
        return this.request;
    }

}
