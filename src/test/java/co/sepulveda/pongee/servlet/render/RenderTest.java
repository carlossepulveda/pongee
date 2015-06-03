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
package co.sepulveda.pongee.servlet.render;

import co.sepulveda.pongee.servlet.http.Response;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author carlossepulveda
 */
public class RenderTest {

    @Test
    public void shouldRenderStatus() throws IOException {
        Response response = new Response();
        response.setStatus(200);

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(httpResponse).setStatus(eq(200));
    }

    @Test
    public void shouldRenderHeaders() throws IOException {
        Response response = new Response();
        response.addHeader("X-Custom-Header1", "custom-value1");
        response.addHeader("X-Custom-Header2", "custom-value2");

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(httpResponse, times(2)).setHeader(any(String.class),any(String.class));
        verify(httpResponse).setHeader(eq("X-Custom-Header1"),eq("custom-value1"));
        verify(httpResponse).setHeader(eq("X-Custom-Header2"),eq("custom-value2"));
    }

    @Test
    public void shouldRenderContentType() throws IOException {
        Response response = new Response();
        response.setContentType("some/content");

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(httpResponse).setContentType("some/content");
    }

    @Test
    public void shouldRenderBody() throws IOException {
        Response response = new Response();
        response.setBody("This is a body example");

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        ServletOutputStream responseWriter = mock(ServletOutputStream.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        when(httpResponse.getOutputStream()).thenReturn(responseWriter);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(responseWriter).print("This is a body example");
    }

    @Test
    public void shouldRenderBodyAsFile() throws IOException {
        URL urlFile = Thread.currentThread().getContextClassLoader()
                .getResource("test-file");
        Response response = new Response();
        response.setFile(urlFile.getFile());

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        ServletOutputStream responseWriter = mock(ServletOutputStream.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        when(httpResponse.getOutputStream()).thenReturn(responseWriter);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(responseWriter, times(1)).flush();
        verify(responseWriter).write(any(byte[].class), any(Integer.class), any(Integer.class));
    }

    @Test
    public void shouldFailRenderIfFileDoesntExist() throws IOException {
        Response response = new Response();
        response.setFile("file-no-exists");

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(httpResponse).setStatus(eq(500));
    }

    @Test
    public void shouldRenderCookies() throws IOException {
        Cookie cookieExample = new Cookie("name", "value");
        Response response = new Response();
        response.addCookie(cookieExample);

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(httpResponse).addCookie(eq(cookieExample));
    }

    @Test
    public void shouldRenderRedirect() throws IOException {
        Response response = Response.redirect("/some/path");

        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        Render render = new Render();
        render.render(httpRequest, httpResponse, response);
        verify(httpResponse).sendRedirect(eq("/some/path"));
    }
}