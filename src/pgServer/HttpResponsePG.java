package pgServer;

import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */

public class HttpResponsePG{

    private HttpServletResponse response;

    public HttpResponsePG(HttpServletResponse response) {
        this.response = response;
    }

    public void send(String msj) {
        try{
            response.getWriter().print(msj);
        } catch ( Exception e) {
            System.err.println("Error - Print response message");
        }
    }

    public HttpServletResponse getHttpServletResponse(){
        return this.response;
    }
}
