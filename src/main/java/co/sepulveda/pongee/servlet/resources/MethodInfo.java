package co.sepulveda.pongee.servlet.resources;

import java.lang.reflect.Method;

/**
 *
 * @author Carlos Sepulveda
 */
public class MethodInfo {

    private final Object controller;
    private final Method method;

    public MethodInfo(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
