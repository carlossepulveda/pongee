package co.sepulveda.pongee.servlet.resources.parser;

import co.sepulveda.pongee.annotations.DELETE;
import co.sepulveda.pongee.annotations.GET;
import co.sepulveda.pongee.annotations.POST;
import co.sepulveda.pongee.annotations.PUT;
import co.sepulveda.pongee.annotations.Resource;
import co.sepulveda.pongee.servlet.http.HttpMethod;
import co.sepulveda.pongee.servlet.resources.MethodInfo;
import co.sepulveda.pongee.servlet.resources.ResourceEntity;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/**
 *
 * @author Carlos Sepulveda
 */
public class ResourcesParser {

    public static List<ResourceEntity> parse(String packagename, List<Object> objectsControllers) {
        List<ResourceEntity> allResources = new ArrayList();

        if (packagename != null && !packagename.isEmpty()) {
            List<ResourceEntity> packageResources = parsePackage(packagename);
            addResources(allResources, packageResources);
        }

        if (objectsControllers != null && !objectsControllers.isEmpty()) {
            List<ResourceEntity> objectsResources = parseObjects(objectsControllers);
            addResources(allResources, objectsResources);
        }

        return allResources;
    }

    private static List<ResourceEntity> parsePackage(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<String> allControllersInPackage = reflections.getStore().getSubTypesOf(Object.class.getName());

        List<ResourceEntity> allResources = new ArrayList();
        for (String className : allControllersInPackage) {
            List<ResourceEntity> resources = getResources(className);
            addResources(allResources, resources);
        }

        return allResources;
    }

    private static List<ResourceEntity> getResources(String classname) {
        Class clazz = getClass(classname);
        if (clazz == null || !clazz.isAnnotationPresent(Resource.class)) {
            return Collections.emptyList();
        }

        Object controller = getInstance(clazz);
        if (controller == null) {
            return Collections.emptyList();
        }

        List<ResourceEntity> resources = new ArrayList();
        for (Method method : controller.getClass().getDeclaredMethods()) {
            ResourceEntity resource = getResource(controller, method);
            if (resource != null) {
                resources.add(resource);
            }
        }

        return resources;
    }

    private static List<ResourceEntity> parseObjects(List<Object> controllers) {
        List<ResourceEntity> allResources = new ArrayList();
        for (Object controller : controllers) {
            List<ResourceEntity> resources = getResources(controller);
            addResources(allResources, resources);
        }

        return allResources;
    }

    private static List<ResourceEntity> getResources(Object controller) {
        if (controller == null) {
            return Collections.emptyList();
        }

        if (!controller.getClass().isAnnotationPresent(Resource.class)) {
            return Collections.emptyList();
        }

        List<ResourceEntity> resources = new ArrayList();
        for (Method method : controller.getClass().getDeclaredMethods()) {
            ResourceEntity resource = getResource(controller, method);
            if (resource != null) {
                resources.add(resource);
            }
        }

        return resources;
    }

    private static void addResources(List<ResourceEntity> allResources, List<ResourceEntity> resources) {
        for (ResourceEntity resource : resources) {
            int i = allResources.indexOf(resource);
            if (i != -1) {
                allResources.get(i).addMethods(resource.getMethods());
            } else {
                allResources.add(resource);
            }
        }
    }

    private static Class getClass(String classname) {
        try {
            return Class.forName(classname);
        } catch (Exception e) {
            return null;
        }
    }

    private static Object getInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    private static String buildAbsolutePath(String controllerPath, String methodPath) {

        String absolutePath = controllerPath;
        if (!methodPath.isEmpty()) {
            absolutePath += "/" + methodPath;
        }

        return absolutePath;
    }

    private static ResourceEntity buildResource(HttpMethod httpMethod, String absolutePath, Object controller, Method method) {
        ResourceEntity resource = new ResourceEntity(absolutePath);
        MethodInfo methodInfo = new MethodInfo(controller, method);
        resource.addMethod(httpMethod, methodInfo);

        return resource;
    }

    private static ResourceEntity getResource(Object controller, Method method) {
        Resource resourceAnnotation = (Resource) controller.getClass().getAnnotation(Resource.class);
        String controllerPath = resourceAnnotation.name();

        HttpMethod httpMethod = null;
        String methodPath = null;
        if (method.isAnnotationPresent(GET.class)) {
            GET getMethod = method.getAnnotation(GET.class);
            methodPath = getMethod.path();
            httpMethod = HttpMethod._GET;
        }

        if (method.isAnnotationPresent(POST.class)) {
            POST postMethod = method.getAnnotation(POST.class);
            methodPath = postMethod.path();
            httpMethod = HttpMethod._POST;
        }

        if (method.isAnnotationPresent(PUT.class)) {
            PUT putMethod = method.getAnnotation(PUT.class);
            methodPath = putMethod.path();
            httpMethod = HttpMethod._PUT;
        }

        if (method.isAnnotationPresent(DELETE.class)) {
            DELETE deleteMethod = method.getAnnotation(DELETE.class);
            methodPath = deleteMethod.path();
            httpMethod = HttpMethod._DELETE;
        }

        if (httpMethod == null) {
            return null;
        }

        String absolutePath = buildAbsolutePath(controllerPath, methodPath);
        return buildResource(httpMethod, absolutePath, controller, method);
    }
}
