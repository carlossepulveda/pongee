package co.sepulveda.pongee.servlet;

import co.sepulveda.pongee.Configuration;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/**
 *
 * @author Carlos Sepulveda
 */
public class ServletHandler extends HttpServlet {

    private final ControllerExecutor controllerExecutor;
    private final StaticExecutor staticExecutor;
    private final Configuration config;

    public ServletHandler(Configuration config) {
        this.config = config;

        String controllers = config.getControllers();
        Reflections reflections = new Reflections(controllers, new SubTypesScanner(false));
        Set<String> allControllers = reflections.getStore().getSubTypesOf(Object.class.getName());

        controllerExecutor = new ControllerExecutor(allControllers);
        staticExecutor = new StaticExecutor();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getPathInfo().startsWith(config.getStaticPath())) {
            staticExecutor.execute(req, resp);
        } else {
            controllerExecutor.execute(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controllerExecutor.execute(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controllerExecutor.execute(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controllerExecutor.execute(req, resp);
    }
}
