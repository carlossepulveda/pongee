package co.sepulveda.pongee.servlet;

import co.sepulveda.pongee.annotations.DELETE;
import co.sepulveda.pongee.annotations.GET;
import co.sepulveda.pongee.annotations.POST;
import co.sepulveda.pongee.annotations.PUT;
import co.sepulveda.pongee.annotations.Resource;
import co.sepulveda.pongee.servlet.render.Render;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carlos Sepulveda
 */
public class ControllerExecutor {

    private final Set<String> allControllers;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    public ControllerExecutor(Set<String> allControllers) {
        this.allControllers = allControllers;
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Response responsePG = execute_(req, resp);
        render(req, resp, responsePG);
    }

    public Response execute_(HttpServletRequest req, HttpServletResponse resp) {
        Class controller = getController(req.getPathInfo());
        if (controller == null) {
            return buildNotFoundResponse(req, resp);
        }

        Method controllerMethod = getControllerMethod(controller, req.getMethod());
        if (controllerMethod == null) {
            return buildNotFoundResponse(req, resp);
        }

        return executeController(controller, controllerMethod, req, resp);
    }

    private Response executeController(Class controller, Method method, HttpServletRequest req, HttpServletResponse resp) {
        try {
            Request requestPG = new Request(req);
            Method m_e = controller.getMethod(method.getName(), requestPG.getClass());
            Response responsePG = (Response) m_e.invoke(controller.newInstance(), requestPG);

            return responsePG;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            return buildInternalErrorResponse(req, resp);
        }
    }

    private Response buildInternalErrorResponse(HttpServletRequest req, HttpServletResponse response) {
        Response responsePG = new Response();
        response.setStatus(500);
        response.setContentType("application/json");
        responsePG.setBody("Internal Error");

        return responsePG;
    }

    private Response buildNotFoundResponse(HttpServletRequest req, HttpServletResponse response) {
        Response responsePG = new Response();
        response.setStatus(404);
        response.setContentType("application/json");
        responsePG.setBody("Not found");

        return responsePG;
    }

    private void render(HttpServletRequest req, HttpServletResponse response, Response responsePG) throws IOException {
        Render render = new Render();
        render.render(req, response, responsePG);
    }

    private Class getController(String resource) {
        for (String className : allControllers) {
            if (match(resource, className)) {
                return getClass(resource);
            }
        }
        return null;
    }

    private Class getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean match(String resource, String controllerName) {
        Class clazz = getClass(controllerName);
        if (clazz == null) {
            return false;
        }

        if (!clazz.isAnnotationPresent(Resource.class)) {
            return false;
        }

        Resource resourceAnnotation = (Resource) clazz.getAnnotation(Resource.class);
        return resource.equals(resourceAnnotation.name());
    }

    private Method getControllerMethod(Class class_, String apiMethod) {
        Class annotationMethod = getMethodAnnotation(apiMethod);
        for (Method method : class_.getDeclaredMethods()) {
            if (Modifier.toString(method.getModifiers()).equals("public")
                    && method.isAnnotationPresent(annotationMethod)) {
                return method;
            }
        }
        return null;
    }

    private static Class getMethodAnnotation(String method) {
        if (method.equals(GET)) {
            return GET.class;
        }
        if (method.equals(POST)) {
            return POST.class;
        }
        if (method.equals(PUT)) {
            return PUT.class;
        }
        if (method.equals(DELETE)) {
            return DELETE.class;
        }

        return null;
    }
}
