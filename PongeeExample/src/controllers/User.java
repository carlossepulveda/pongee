package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.asfun.jangod.template.TemplateEngine;
import pgServer.HttpRequestPG;
import pgServer.HttpResponsePG;

public class User {

    public void getId(HttpRequestPG req, HttpResponsePG resp) throws IOException{

        String idUser = req.getURLParam("id");
        
        //Respuesta haciendo uso de template engine
        TemplateEngine engine = new TemplateEngine();
        String workSpaceTemplates = "templates";
        engine.getConfiguration().setWorkspace( workSpaceTemplates );

        Map<String,Object> data = new HashMap<String, Object>();
        data.put("id", idUser);

        String html = engine.process("user.html", data);
        resp.send( html );

    }

}
