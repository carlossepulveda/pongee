<h1>Pongee</h1>
======

Micro-framework web java

<pre>
import co.sepulveda.pongee.Configuration;
import co.sepulveda.pongee.ServerPG;

public class Main {

    public static void main(String[] args) {
        Configuration conf = Configuration.create()
        	//nombre del paquete que contiene los controladores
                .withControllers("my.controllers");

        new ServerPG(conf).listen();
    }
}
</pre>

Ejemplo GET
<pre>
package my.controllers;

import co.sepulveda.pongee.annotations.GET;
import co.sepulveda.pongee.annotations.Resource;
import co.sepulveda.pongee.servlet.Request;
import co.sepulveda.pongee.servlet.Response;

@Resource(name="/resource")
public class Test {

    @GET
    public Response someMehthod(Request request) {
        return new Response()
                .withBody("Some response");
    }
}
</pre>

Ejemplo POST - Lectura del request <br/>
Request Body : {"name":"Carlos"}
<pre>
package my.controllers;

import co.sepulveda.pongee.annotations.POST;
import co.sepulveda.pongee.annotations.Resource;
import co.sepulveda.pongee.servlet.Request;
import co.sepulveda.pongee.servlet.Response;

@Resource(name="/resource")
public class Test {

    @POST
    public Response someMehthod(Request request) {
        RegisterForm form = request.parseBody(RegisterForm.class);
        if (form == null) {
            return Response
                    .badRequest()
                    .withBody("Invalid Data");
        }

        System.out.println("Name : " + registerForm.getName());

        return new Response()
                .withBody("ok")
                .withStatus(201);
    }
}
</pre>
<pre>
public class RegisterForm {
    private String name;
    
    public String getName() {
        return name;
    }
    public String setName(String name) {
        this.name = name;
    }
}
</pre>

Ejemplo renderizando objetos
<pre>
package my.controllers;

import co.sepulveda.pongee.annotations.POST;
import co.sepulveda.pongee.annotations.Resource;
import co.sepulveda.pongee.servlet.Request;
import co.sepulveda.pongee.servlet.Response;

@Resource(name="/resource")
public class Test {

    @GET
    public Response someMehthod(Request request) {
        Person person = new Person();
        person.setName("Carlos Andres");

        return new Response()
                .withBody(person);
    }
}
</pre>
<pre>
public class Person {
    private String name;
    
    public String getName() {
        return name;
    }
    public String setName(String name) {
        this.name = name;
    }
}
</pre>
