/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import pgServer.HttpRequestPG;
import pgServer.HttpResponsePG;

/**
 *
 * @author elibom
 */
public class Home {

    public void getHome(HttpRequestPG req, HttpResponsePG resp) throws IOException{

        resp.getHttpServletResponse().sendRedirect("/static/index.html");

    }
}
