package co.sepulveda.pongee.servlet.resources;

import co.sepulveda.pongee.servlet.http.HttpMethod;
import co.sepulveda.pongee.util.PathParser;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Carlos Sepulveda
 */
public class ResourceEntity {

    private final String regexUrl;
    private final Map<Enum, MethodInfo> methodsInfo;

    public ResourceEntity(String regexUrl) {
        this.regexUrl = regexUrl;
        methodsInfo = new HashMap();
    }

    public void addMethod(HttpMethod method, MethodInfo info) {
        methodsInfo.put(method, info);
    }

    public void addMethods(Map<Enum, MethodInfo> methodsInfo) {
        for (Entry<Enum, MethodInfo> entry : methodsInfo.entrySet()) {
            this.methodsInfo.put(entry.getKey(), entry.getValue());
        }
    }

    public MethodInfo getMethod(HttpMethod method) {
        return methodsInfo.get(method);
    }

    public Map<Enum, MethodInfo> getMethods() {
        return methodsInfo;
    }

    public boolean match(String path) {
        ResourceEntity resource = new ResourceEntity(path);
        return equals(resource);
    }

    public String getRegexUrl() {
        return regexUrl;
    }

    @Override
    public String toString() {
        String response = "";
        for (Entry<Enum, MethodInfo> entry : methodsInfo.entrySet()) {
            String httpMethod = entry.getKey().toString();
            Object controller = entry.getValue().getController();
            Method method = entry.getValue().getMethod();
            response += "\n" + regexUrl + " " + httpMethod + " " + controller.getClass().getCanonicalName() + " " + method.getName();
        }

        return response;
    }

    @Override
    public boolean equals(Object resource) {
        ResourceEntity compared = (ResourceEntity) resource;
        if (compared.getRegexUrl().equals(regexUrl)) {
            return true;
        }

        String[] comparedData = PathParser.getRegexPathAsArray(compared.getRegexUrl());
        String[] meData = PathParser.getRegexPathAsArray(regexUrl);
        if (comparedData.length != meData.length) {
            return false;
        }

        for (int i = 0; i < comparedData.length; i++) {
            String meElement = meData[i];
            String comparedElement = comparedData[i];
            if (!meElement.equals("::") && !comparedElement.equals("::") && !meElement.equals(comparedElement)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String [] ar) {
        ResourceEntity r1 = new ResourceEntity("/example/000000");
        ResourceEntity r2 = new ResourceEntity("/example/:id");
        System.out.println(r1.equals(r2));
    }
}
