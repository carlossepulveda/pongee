package co.sepulveda.pongee;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Sepulveda
 */
public class Configuration {

    private int port = 3000;

    private String controllersPackage;

    private List<Object> objectControllers;

    private String staticPath = "/static";

    public Configuration() {
        this.objectControllers = new ArrayList();
    }

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

    public String getControllersPackage() {
        return controllersPackage;
    }

    public void setControllersPackage(String controllersPackage) {
        this.controllersPackage = controllersPackage;
    }

    public List<Object> getObjectControllers() {
        return objectControllers;
    }

    public Configuration withControllersPackage(String controllers) {
        setControllersPackage(controllers);
        return this;
    }

    public Configuration withController(Object controller) {
        objectControllers.add(controller);
        return this;
    }

    public Configuration withControllers(List<Object> controllers) {
        objectControllers = controllers;
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
