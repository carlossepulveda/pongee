package co.sepulveda.pongee.servlet;

import co.sepulveda.pongee.Configuration;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        controllerExecutor = new ControllerExecutor(config.getControllersPackage(), config.getObjectControllers());
        staticExecutor = new StaticExecutor();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setGlobalConfiguration(req, resp);

        if (req.getPathInfo().startsWith(config.getStaticPath())) {
            staticExecutor.execute(req, resp);
        } else {
            controllerExecutor.execute(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setGlobalConfiguration(req, resp);
        controllerExecutor.execute(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setGlobalConfiguration(req, resp);
        controllerExecutor.execute(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setGlobalConfiguration(req, resp);
        controllerExecutor.execute(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setGlobalHeaders(req, resp);
        super.doOptions(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setGlobalHeaders(req, resp);
        super.doTrace(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setGlobalHeaders(req, resp);
        super.doHead(req, resp);
    }

    private void setGlobalConfiguration(HttpServletRequest req, HttpServletResponse resp) {
        setGlobalHeaders(req, resp);
    }

    private void setGlobalHeaders(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> headers = config.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            resp.setHeader(entry.getKey(), entry.getValue());
        }
    }
}
