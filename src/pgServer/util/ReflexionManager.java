package pgServer.util;

import java.lang.reflect.Method;
import pgServer.HttpRequestPG;
import pgServer.HttpResponsePG;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */

public class ReflexionManager {

    public static void executeControllerMethod (String className, String methodName, HttpRequestPG req, HttpResponsePG res) {
      try{
          
            Class<?> c = Class.forName(className);
            Object o = c.newInstance();
            Object [] params = new Object[]{req,res};
            Class[] parameterTypes = new Class[] { HttpRequestPG.class , HttpResponsePG.class };
            Method  method = c.getDeclaredMethod ( methodName, parameterTypes);
            method.invoke (o,params);

        } catch ( Exception e ) {
            System.err.println("Error -  Executing controller method : "+e.toString());
        }
    }
}
