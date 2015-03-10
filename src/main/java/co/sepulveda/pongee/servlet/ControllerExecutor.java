package co.sepulveda.pongee.servlet;

import co.sepulveda.pongee.util.PathParser;
import co.sepulveda.pongee.servlet.http.Response;
import co.sepulveda.pongee.servlet.http.Request;
import co.sepulveda.pongee.servlet.http.HttpMethod;
import co.sepulveda.pongee.servlet.render.Render;
import co.sepulveda.pongee.servlet.resources.MethodInfo;
import co.sepulveda.pongee.servlet.resources.ResourceEntity;
import co.sepulveda.pongee.servlet.resources.parser.ResourcesParser;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carlos Sepulveda
 */
public class ControllerExecutor {

    private final List<ResourceEntity> resources;

    public ControllerExecutor(String packageName, List<Object> objectsControllers) {
        resources = ResourcesParser.parse(packageName, objectsControllers);
        printResources(resources);
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Response responsePG = execute_(req, resp);
        render(req, resp, responsePG);
    }

    public Response execute_(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ResourceEntity resource = getResource(req.getPathInfo());
            if (resource == null) {
                return buildNotFoundResponse(req, resp);
            }

            HttpMethod httpMethod = HttpMethod.getMethod(req.getMethod());
            MethodInfo methodInfo = resource.getMethod(httpMethod);
            if (methodInfo == null) {
                return buildNotFoundResponse(req, resp);
            }

            Map<String, String> urlParams = PathParser.getData(req.getPathInfo(), resource.getRegexUrl());
            Object controller = methodInfo.getController();
            Method method = methodInfo.getMethod();
            Request requestPG = new Request(req, urlParams);
            Response responsePG = (Response) method.invoke(controller, requestPG);

            return responsePG;

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            return buildInternalErrorResponse(req, resp);
        }
    }

    private ResourceEntity getResource(String path) {
        for (ResourceEntity resource : resources) {
            if (resource.match(path)) {
                return resource;
            }
        }

        return null;
    }

    private Response buildInternalErrorResponse(HttpServletRequest req, HttpServletResponse response) {
        Response responsePG = Response.internalError()
                .withBody("Internal Error");

        return responsePG;
    }

    private Response buildNotFoundResponse(HttpServletRequest req, HttpServletResponse response) {
        Response responsePG = Response.notFound()
                .withBody("Not found");

        return responsePG;
    }

    private void render(HttpServletRequest req, HttpServletResponse response, Response responsePG) throws IOException {
        Render render = new Render();
        render.render(req, response, responsePG);
    }

    private void printResources(List<ResourceEntity> resources) {
        System.out.println("Registered Resources");
        for (ResourceEntity resource : resources) {
            for (Map.Entry<Enum, MethodInfo> entry : resource.getMethods().entrySet()) {
                String httpMethod = entry.getKey().toString();
                Object controller = entry.getValue().getController();
                Method method = entry.getValue().getMethod();
                System.out.println("\t" + httpMethod 
                        + " " + resource.getRegexUrl()
                        + " " + controller.getClass().getCanonicalName() 
                        + " " + method.getName());
            }
        }
    }
}
