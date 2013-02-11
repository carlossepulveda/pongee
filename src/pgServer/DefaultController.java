package pgServer;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */
public class DefaultController {

    private HttpRequestPG req;
    private HttpResponsePG resp;
    
    public DefaultController( HttpRequestPG req, HttpResponsePG resp ) {
        this.req = req;
        this.resp = resp;
    }

    public void doGET () {
        this.resp.send("Default GET Message serverPG");
    }
    public void doPOST () {
        this.resp.send("Default GET Message serverPG");
    }
    public void doPUT () {
        this.resp.send("Default GET Message serverPG");
    }
    public void doDELETE () {
        this.resp.send("Default GET Message serverPG");
    }

}
