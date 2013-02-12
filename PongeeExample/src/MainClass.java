
import pgServer.ServerPG;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elibom
 */
public class MainClass {

    public static void main(String[] args) {
        ServerPG server = new ServerPG () ;
        String jsonURL = "config/config.json";
        server.setConfigurationFile( jsonURL ).listen();
    }

}
