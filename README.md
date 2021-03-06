#Pongee

A cool micro-framework web implemented in Java, that allows to create a REST server in just a minute. You do not need to configure external content servers or apps containers due to that Pongee is a embedded server. For that reason you just have to write the code and do not worry by the server deployment (You do not need to spend time doing large configurations - Just write code).

## Getting started
You can add the maven dependecy

```xml
<dependency>
    <groupId>co.sepulveda</groupId>
    <artifactId>pongee</artifactId>
    <version>1.2</version>
</dependency>
```
Or downloading the jar file ( http://central.maven.org/maven2/co/sepulveda/pongee/1.1/pongee-1.1.jar )

## Basic code
```java
import co.sepulveda.pongee.Configuration;
import co.sepulveda.pongee.ServerPG;

public class Main {

    public static void main(String[] args) {
        Configuration conf = Configuration.create()
                .withControllersPackage("my.controllers.package");
        	    //name of package that contains the endpoints controllers

        new ServerPG(conf).listen();
    }
}
```
Server running on 3000 (default port)

##Controller - GET
```java
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
```
Put in the browser [http://localhost:3000/resource](http://localhost:3000/resource)

##Controller - POST
###Reading request
POST  /resource  {"name":"Carlos"}
```java
package my.controllers;

import co.sepulveda.pongee.annotations.POST;
import co.sepulveda.pongee.annotations.Resource;
import co.sepulveda.pongee.servlet.Request;
import co.sepulveda.pongee.servlet.Response;

@Resource(name="/resource")
public class Test {

    @POST
    public Response singUp(Request request) {
        RegisterForm form = request.parseBody(RegisterForm.class);
        if (form == null) {
            return Response.badRequest().withBody("Invalid Data");
        }

        System.out.println("Name : " + registerForm.getName());

        return new Response().withStatus(201).withBody("Record saved successfully");
    }
}
```
```java
package my.forms;

public class RegisterForm {
    private String name;
    
    public String getName() {
        return name;
    }
    public String setName(String name) {
        this.name = name;
    }
}
```

##Rendering objects
```java
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
```
```java
package my.web.entities;

public class Person {
    private String name;
    
    public String getName() {
        return name;
    }
    public String setName(String name) {
        this.name = name;
    }
}
```
Put in the browser [http://localhost:3000/resource](http://localhost:3000/resource)

##Injecting controllers

You can use a pre-initialized object as controller (some usages could be the bean injection). When you are initializing the configuration, you can use both ways (controllers package or object).

```java
import co.sepulveda.pongee.Configuration;
import co.sepulveda.pongee.ServerPG;

public class Main {

    public static void main(String[] args) {
        my.controllers.Test testController = new my.controllers.Test();
        Configuration conf = Configuration.create()
        	    .withController(testController);
        	    //.withControllers(listOfControllers);

        new ServerPG(conf).listen();
    }
}
```

##Implementing your integration tests



```java
package co.sepulveda.some.package.test;

import co.sepulveda.pongee.Configuration;
import co.sepulveda.pongee.servlet.http.HttpMethod;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author carlossepulveda
 */
public class SomeControllerTest {

    @Test
    public void shouldRender() throws Exception {
        Configuration conf = new Configuration().withControllersPackage("controllers.package");
        MockServer server = MockServer.build(conf);

        MockRequest request = new MockRequest()
                .withUrl("/person")
                .withMethod(HttpMethod._GET)
                .withContentType("text/plain")
                .withBody("body");

        MockResponse response = server.run(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("your_response", response.getBodyAsString())
    }
}
```
