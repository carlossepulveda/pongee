package co.sepulveda.pongee;

import co.sepulveda.pongee.servlet.ServletHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author Carlos Sepulveda
 */
public class ServerPG {

    private static final String STATE_UP = "up";
    private static final String STATE_DOWN = "down";
    private final Configuration config;
    private String state;

    public ServerPG(String controllers, int port) {
        this.config = Configuration.create();
        config.setPackageControllers(controllers);
        config.setPort(port);
    }

    public ServerPG(Configuration configuration) {
        this.config = configuration;
        setState(ServerPG.STATE_DOWN);
    }

    public void listen() {
        try {
            runServer();
        } catch ( Exception e) {
            System.out.println("Error - Trying run server");
        }
    }

    private void runServer () throws Exception {
        if ( state.equals(ServerPG.STATE_DOWN ) ) {
            setState(ServerPG.STATE_UP );
            Server server = new Server(config.getPort());

            ServletContextHandler context0 = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context0.setContextPath("/");
            ServletHandler svlHandler = new ServletHandler(config);
            ServletHolder svlHolder = new ServletHolder(svlHandler);
            context0.addServlet(svlHolder , "/*");

            ResourceHandler resource_handler = new ResourceHandler();
            resource_handler.setDirectoriesListed(true);
            resource_handler.setWelcomeFiles(new String[]{ "index.html" });

            resource_handler.setResourceBase(".");
        
            ContextHandlerCollection contexts = new ContextHandlerCollection();
            contexts.setHandlers(new Handler[] { context0 , resource_handler} );

            server.setHandler(contexts);

            server.start();
            server.join();
            
            System.out.println("Server running in " + config.getPort());

        } else {
            System.err.println("Error - Server is running already");
        }
    }

    public String getState() {
        return state;
    }

    private void setState(String state) {
        this.state = state;
    }
}
