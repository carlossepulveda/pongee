package pgServer;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */
public class ServerPG {

    private ConfigurationServer configurations;
    private static final String STATE_UP = "up";
    private static final String STATE_DOWN = "down";
    private String state;

    public ServerPG() {
        this.setState(ServerPG.STATE_DOWN);
        configurations = new ConfigurationServer();
    }

    public int getPort() {
        return configurations.getPort();
    }

    public void listen() {
        if ( configurations.getPort() <=0 ){
            configurations.setPort(3000);
        }
        try {
            this.runServer();
        } catch ( Exception e) {
            System.out.println("Error - Trying run server");
        }
    }

    public void listen( int port ) {
        if ( port>0) {
            configurations.setPort(port);
            listen();
        } else {
            System.err.println("Error - Invalid Port");
        }
    }

    private void runServer () throws Exception {
        
        if ( state.equals( ServerPG.STATE_DOWN ) ) {
            
            this.setState( ServerPG.STATE_UP );
            Server server = new Server( configurations.getPort() );

            ServletContextHandler context0 = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context0.setContextPath("/");
            context0.addServlet(
                    new ServletHolder(new ServletHandlerPG(configurations.getRestServicesInfo())) , "/*"
                    );

            ResourceHandler resource_handler = new ResourceHandler();
            resource_handler.setDirectoriesListed(true);
            resource_handler.setWelcomeFiles(new String[]{ "index.html" });

            resource_handler.setResourceBase(".");
        
            ContextHandlerCollection contexts = new ContextHandlerCollection();
            contexts.setHandlers(new Handler[] { context0 , resource_handler} );

            server.setHandler(contexts);

            server.start();
            server.join();
            
            System.out.println("Server running in "+configurations.getPort());

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

    public ConfigurationServer getConfigurations() {
        return configurations;
    }

    public void setConfigurations(ConfigurationServer configurations) {
        this.configurations = configurations;
    }

    public ServerPG setConfigurationFile ( String fileUrl ) {
        configurations.setConfigurationFile(fileUrl);
        return this;
    }

    


}
