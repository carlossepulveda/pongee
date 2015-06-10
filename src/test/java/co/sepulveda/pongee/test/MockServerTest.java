/*
 * Copyright 2015 carlossepulveda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.sepulveda.pongee.test;

import co.sepulveda.pongee.Configuration;
import co.sepulveda.pongee.servlet.http.HttpMethod;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author carlossepulveda
 */
public class MockServerTest {

    @Test
    public void shouldExecuteMockServer() throws Exception {
        Configuration conf = new Configuration();
        conf.withControllersPackage("co.sepulveda.pongee.fake.data.controllers");
        conf.withHeader("x-header-x", "value");
        conf.withStaticPath("static");

        MockServer server = MockServer.build(conf);

        String cookieValue = "cookie-value";
        Cookie cookieTest = new Cookie("test-cookie", cookieValue);
        String body = "body";
        MockRequest request = new MockRequest()
                .withUrl("/person")
                .withMethod(HttpMethod._GET)
                .withContentType("text/plain")
                .withBody(body)
                .withCookie(cookieTest);

        MockResponse response = server.run(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("value", response.getHeader("x-header-x"));
        Assert.assertEquals(body + " response " + cookieValue, response.getBodyAsString());
    }

    @Test
    public void shouldExecuteMockServer404() throws Exception {
        Configuration conf = new Configuration();
        conf.withControllersPackage("co.sepulveda.pongee.fake.data.controllers");
        conf.withHeader("x-header-x", "value");
        conf.withStaticPath("static");

        MockServer server = MockServer.build(conf);

        Cookie cookieTest = new Cookie("test-cookie", "cookie-value");
        MockRequest request = new MockRequest()
                .withUrl("/some/not/found/resource")
                .withMethod(HttpMethod._GET)
                .withContentType("text/plain")
                .withBody("body")
                .withCookie(cookieTest);

        MockResponse response = server.run(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatus());
        Assert.assertEquals("value", response.getHeader("x-header-x"));
    }
}
