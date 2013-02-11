package pgServer;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */

public class ConfigurationServer {

    private int port;
    private RestServicesInfo[] restServicesInfo;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public RestServicesInfo[] getRestServicesInfo() {
        return restServicesInfo;
    }

    public void setRestServicesInfo(RestServicesInfo[] restServicesInfo) {
        this.restServicesInfo = restServicesInfo;
    }

    public void setConfigurationFile ( String fileUrl ) {
        String jsonText = ConfigurationServer.readFile(fileUrl);

        if ( jsonText != null) {

            Gson gs = new Gson();
            ConfigurationServer x = gs.fromJson(jsonText,ConfigurationServer.class);
            this.setPort(x.getPort());
            this.setRestServicesInfo(x.getRestServicesInfo());

        } else {
            System.err.println( "Error - Was imposible read configuration file" );
        }
    }

    public static String readFile (String ruta) {
        try {

            String linea;
            FileReader fr = new FileReader (ruta);
            BufferedReader br = new BufferedReader(fr);
            String text = "";
            while((linea=br.readLine())!=null){
                text += "\n"+linea;
            }
            return text;

        }catch(IOException e)
        {
            System.err.println("Error - Reading configuration file");
            return null;
        }
    }
    @Override
    public String toString() {
        String rsI = "";
        for ( RestServicesInfo rs : restServicesInfo ) {
            rsI+= rs.toString()+";";
        }
        return "ConfigurationServer{\n" + "\tport=" + port + ", \n\trestServicesInfo=" + rsI  + "\n};";
    }




}
