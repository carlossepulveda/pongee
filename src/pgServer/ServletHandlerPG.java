package pgServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pgServer.util.ReflexionManager;
import pgServer.util.URLMatcher;

/**
 *
 * @author Carlos Andres Sepulveda Sanchez
 * http://github.com/carlossepulveda
 */
public class ServletHandlerPG extends HttpServlet{

    private RestServicesInfo[] restServices;
    private ArrayList<RestServicesInfo> restServicesGET;
    private ArrayList<RestServicesInfo> restServicesPOST;
    private ArrayList<RestServicesInfo> restServicesPUT;
    private ArrayList<RestServicesInfo> restServicesDELETE;

    public static String GET_METHOD = "GET";
    public static String POST_METHOD = "POST";
    public static String PUT_METHOD = "PUT";
    public static String DELETE_METHOD = "DELETE";

    public static String ERROR_404="404";

    public final Map<String, Object> staticResources;


    public ServletHandlerPG( RestServicesInfo[] restServices) {
        this.restServices = restServices;
        staticResources = new HashMap<String, Object>();

        filterServices();
        
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpRequestPG httpRPG = new HttpRequestPG(req);
            HttpResponsePG httpResPG = new HttpResponsePG(resp);
            String[] spl = req.getPathInfo().split("/");
            String firstStringURL =  spl[1];
            if ( !firstStringURL .equals("static")) {

                for ( RestServicesInfo rs : this.restServicesGET) {
                    if ( URLMatcher.isMatching(rs.getUrl(),req.getPathInfo()) ) {
                       httpRPG.createQueryParams(rs.getUrl());
                       ReflexionManager.executeControllerMethod(rs.getNameController(),rs.getNameMethod(), httpRPG, httpResPG);
                       return;
                    }
                }
                DefaultController dfc = new DefaultController( httpRPG, httpResPG);
                dfc.doGET();

            } else {
                doStatic(req,resp);
            }
            
            
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpRequestPG httpRPG = new HttpRequestPG(req);
        HttpResponsePG httpResPG = new HttpResponsePG(resp);
        for ( RestServicesInfo rs : this.restServicesPOST) {
                if ( URLMatcher.isMatching(rs.getUrl(),req.getPathInfo()) ) {
                   ReflexionManager.executeControllerMethod(rs.getNameController(),rs.getNameMethod(), httpRPG, httpResPG);
                   return;
                }
            }
        DefaultController dfc = new DefaultController( httpRPG, httpResPG);
        dfc.doPOST();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpRequestPG httpRPG = new HttpRequestPG(req);
        HttpResponsePG httpResPG = new HttpResponsePG(resp);
        for ( RestServicesInfo rs : this.restServicesPUT) {
                if ( URLMatcher.isMatching(rs.getUrl(),req.getPathInfo()) ) {
                   ReflexionManager.executeControllerMethod(rs.getNameController(),rs.getNameMethod(), httpRPG, httpResPG);
                   return;
                }
            }
        DefaultController dfc = new DefaultController( httpRPG, httpResPG);
        dfc.doPUT();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpRequestPG httpRPG = new HttpRequestPG(req);
        HttpResponsePG httpResPG = new HttpResponsePG(resp);
        for ( RestServicesInfo rs : this.restServicesPUT) {
                if ( URLMatcher.isMatching(rs.getUrl(),req.getPathInfo()) ) {
                   ReflexionManager.executeControllerMethod(rs.getNameController(),rs.getNameMethod(), httpRPG, httpResPG);
                   return;
                }
            }
        DefaultController dfc = new DefaultController( httpRPG, httpResPG);
        dfc.doDELETE();
    }

    private void filterServices() {
        if ( restServices != null ) {
            this.restServicesGET = new ArrayList<RestServicesInfo>();
            this.restServicesPOST = new ArrayList<RestServicesInfo>();
            this.restServicesPUT = new ArrayList<RestServicesInfo>();
            this.restServicesDELETE = new ArrayList<RestServicesInfo>();
            for( RestServicesInfo rs : restServices ) {

                if ( rs.getMethod().toUpperCase().equals(ServletHandlerPG.GET_METHOD)) {
                    this.restServicesGET.add(rs);
                }
                if ( rs.getMethod().toUpperCase().equals(ServletHandlerPG.POST_METHOD)) {
                    this.restServicesPOST.add(rs);
                }
                if ( rs.getMethod().toUpperCase().equals(ServletHandlerPG.PUT_METHOD)) {
                    this.restServicesPUT.add(rs);
                }
                if ( rs.getMethod().toUpperCase().equals(ServletHandlerPG.DELETE_METHOD)) {
                    this.restServicesDELETE.add(rs);
                }

            }

        }
        
        
    }

   public void doStatic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       
       if (! ServletHandlerPG.printFile(req.getPathInfo().substring(1), resp.getOutputStream()) ) {
           ServletHandlerPG.printErrorCode(resp.getOutputStream(), ServletHandlerPG.ERROR_404);
       } 
    
  }

   public static boolean printFile(String urlFile, ServletOutputStream os) {
       try{
            File file = new File(urlFile);
            FileInputStream input = new FileInputStream(file);
            FileChannel channel = input.getChannel();
            byte[] buffer = new byte[256 * 1024];
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
             try {
                 for (int length = 0; (length = channel.read(byteBuffer)) != -1;) {
                     os.write(buffer, 0, length);
                     byteBuffer.clear();
                 }
                 return true;
             } catch ( Exception e ) {
                 return false;
             } finally {
                 input.close();
             }
       } catch ( Exception e) {
           return false;
       }
      
   }

   public static void printErrorCode(ServletOutputStream os, String code) throws FileNotFoundException, IOException {
       if ( !ServletHandlerPG.printFile("errors/"+code+".html", os)) {
           os.print("Error : "+code);
       }
   }


    }
