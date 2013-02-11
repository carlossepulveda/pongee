package pgServer;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */

public class RestServicesInfo {

    private String url;
    private String action;
    private String method;

    public RestServicesInfo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNameController() {
        String[] ac = action.split(":");
        try{
            return ac[0];
        } catch ( Exception e) {
            System.err.println("Error - Action parameters");
            return null;
        }
    }

    public String getNameMethod() {
        String[] ac = action.split(":");
        try{
            return ac[1];
        } catch ( Exception e) {
            System.err.println("Error - Action parameters");
            return null;
        }
    }
    @Override
    public String toString() {
        return "RestServicesInfo{" + "url=" + url + ", action=" + action + ", method=" + method + '}';
    }



}
