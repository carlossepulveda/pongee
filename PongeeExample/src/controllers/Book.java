package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.asfun.jangod.template.TemplateEngine;
import pgServer.HttpRequestPG;
import pgServer.HttpResponsePG;

public class Book {

    public void getId(HttpRequestPG req, HttpResponsePG resp) throws IOException{

        String idBook = req.getURLParam("id");
        resp.getHttpServletResponse().setHeader("contentType","html");
        String html = "<html>Respuesta para getId en controlador Book : "+ idBook+"<br/><a href=\"/\">Volver al inicio</a></html>";
        resp.send(html);

    }
}
