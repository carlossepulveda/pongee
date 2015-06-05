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
package co.sepulveda.pongee.servlet;

import co.sepulveda.pongee.Configuration;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author carlossepulveda
 */
public class ServletHandlerTest {

    @Test
    public void shouldSetGlobalConfigurationGET() throws IOException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn("/some/resource");
        HttpServletResponse response = mock(HttpServletResponse.class);

        sh.doGet(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }

    @Test
    public void shouldSetGlobalConfigurationGET_static() throws IOException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn("/static/some.file");
        HttpServletResponse response = mock(HttpServletResponse.class);

        sh.doGet(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }

    @Test
    public void shouldSetGlobalConfigurationPOST() throws IOException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        sh.doPost(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }

    @Test
    public void shouldSetGlobalConfigurationPUT() throws IOException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        sh.doPut(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }

    @Test
    public void shouldSetGlobalConfigurationDELETE() throws IOException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        sh.doDelete(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }

    @Test
    public void shouldSetGlobalConfigurationOPTIONS() throws IOException, ServletException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        sh.doOptions(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }

    @Test
    public void shouldSetGlobalConfigurationTRACE() throws IOException, ServletException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn("/some/resource");
        when(request.getRequestURI()).thenReturn("/some/resource");
        when(request.getProtocol()).thenReturn("http");
        when(request.getHeaderNames()).thenReturn(getHeaderEnumeration());
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        sh.doTrace(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response, atLeast(1)).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response, atLeast(1)).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }

    private Enumeration getHeaderEnumeration() {
        return new Enumeration() {
            @Override
            public boolean hasMoreElements() { return false; }
            @Override
            public String nextElement() { return null; }
        };
    }

    @Test
    public void shouldSetGlobalConfigurationHEAD() throws IOException, ServletException {
        Configuration conf = new Configuration()
                .withHeader("X-global-header", "x-global-value")
                .withHeader("X-global-another-header", "x-global-another-value");
        ServletHandler sh = new ServletHandler(conf);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn("/some/resource");
        HttpServletResponse response = mock(HttpServletResponse.class);

        sh.doHead(request, response);

        verify(response, atLeast(2)).setHeader(any(String.class), any(String.class));
        verify(response, atLeast(1)).setHeader(eq("X-global-header"), eq("x-global-value"));
        verify(response, atLeast(1)).setHeader(eq("X-global-another-header"), eq("x-global-another-value"));
    }
}
