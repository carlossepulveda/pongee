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
package co.sepulveda.pongee.servlet.http;

import co.sepulveda.pongee.fake.data.anyclass.PersonForm;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import junit.framework.Assert;
import org.junit.Test;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author carlossepulveda
 */
public class RequestTest {

    @Test
    public void shouldParseBodyAsString() throws IOException {
        String body = "body";
        InputStream is = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStreamFake(is);

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getInputStream()).thenReturn(servletInputStream);
        Map<String, String> pathVars = null;

        Request request = new Request(httpRequest, pathVars);
        Assert.assertEquals(request.getBodyAsString(), body);
    }

    @Test
    public void shouldParseBodyAsClass() throws IOException {
        String body = "{\"name\":\"carlos\"}";
        InputStream is = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStreamFake(is);

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getInputStream()).thenReturn(servletInputStream);
        Map<String, String> pathVars = null;

        Request request = new Request(httpRequest, pathVars);
        PersonForm form = request.parseBody(PersonForm.class);
        Assert.assertNotNull(form);
        Assert.assertEquals("carlos", form.getName());
    }

    @Test
    public void shouldReturnParameter() throws IOException {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getParameter(eq("name"))).thenReturn("carlos");
        Map<String, String> pathVars = null;

        Request request = new Request(httpRequest, pathVars);
        String parameter = request.getParameter("name");
        Assert.assertNotNull(parameter);
        Assert.assertEquals("carlos", parameter);
    }

    @Test
    public void shouldReturnPathVar() throws IOException {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        Map<String, String> pathVars = new HashMap();
        pathVars.put("id", "777");

        Request request = new Request(httpRequest, pathVars);
        String pathVar = request.getPathVar("id");
        Assert.assertNotNull(pathVar);
        Assert.assertEquals("777", pathVar);
    }

    @Test
    public void shouldReturnCookie() throws IOException {
        Cookie[] cookies = getCookies();
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getCookies()).thenReturn(cookies);
        Map<String, String> pathVars = null;

        Request request = new Request(httpRequest, pathVars);
        Cookie cookie = request.getCookie("cookie.name");
        Assert.assertNotNull(cookie);
        Assert.assertEquals("cookie.value", cookie.getValue());
    }

    private Cookie[] getCookies() {
        return new Cookie[] {new Cookie("cookie.name", "cookie.value")};
    }

    private class ServletInputStreamFake extends ServletInputStream {

        private final InputStream sourceStream;

        public ServletInputStreamFake(InputStream sourceStream) {
            this.sourceStream = sourceStream;
        }

        public final InputStream getSourceStream() {
            return this.sourceStream;
        }

        @Override
        public int read() throws IOException {
            return this.sourceStream.read();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.sourceStream.close();
        }

        @Override
        public boolean isFinished() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isReady() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setReadListener(ReadListener rl) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
