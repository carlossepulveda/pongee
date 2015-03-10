package co.sepulveda.pongee;

/**
 *
 * @author Carlos Sepulveda
 */
public class Configuration {

    private int port = 3000;

    private String controllers = "co.sepulveda.123.123.not.found";

    private String staticPath = "/static";

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Configuration withPort(int port) {
        setPort(port);
        return this;
    }

    public String getControllers() {
        return controllers;
    }

    public void setControllers(String controllers) {
        this.controllers = controllers;
    }

    public Configuration withControllers(String controllers) {
        setControllers(controllers);
        return this;
    }

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    public Configuration withStaticPath(String staticPath) {
        setStaticPath(staticPath);
        return this;
    }

    public static Configuration create() {
        return new Configuration();
    }
}
