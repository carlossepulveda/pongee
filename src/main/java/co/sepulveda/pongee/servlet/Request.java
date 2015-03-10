
package co.sepulveda.pongee.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Carlos Sepulveda
 */

public class Request{

    private final HttpServletRequest request;

    public Request(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getHttpServletRequest() {
        return this.request;
    }

}
