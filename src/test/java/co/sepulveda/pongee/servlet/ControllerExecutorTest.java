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

import java.io.IOException;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author carlossepulveda
 */
public class ControllerExecutorTest {

    @Test
    public void shouldRenderResourceMethod() throws IOException {
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getPathInfo()).thenReturn("/person");
        when(httpRequest.getMethod()).thenReturn("GET");
        when(httpRequest.getCookies()).thenReturn(new Cookie[0]);

        String controllerPackage = "co.sepulveda.pongee.fake.data.controllers";
        List<Object> objectControllers = null;

        ControllerExecutor controllerExecutor = new ControllerExecutor(controllerPackage, objectControllers);
        controllerExecutor.execute(httpRequest, httpResponse);
        verify(httpResponse).setStatus(200);
    }

    @Test
    public void shouldRenderNotFoundIfResourceDoesntExist() throws IOException {
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getPathInfo()).thenReturn("/nothing");
        when(httpRequest.getMethod()).thenReturn("GET");

        String controllerPackage = "co.sepulveda.pongee.fake.data.controllers";
        List<Object> objectControllers = null;

        ControllerExecutor controllerExecutor = new ControllerExecutor(controllerPackage, objectControllers);
        controllerExecutor.execute(httpRequest, httpResponse);
        verify(httpResponse).setStatus(404);
    }

    @Test
    public void shouldRenderNotFoundIfMethodDoesntExist() throws IOException {
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getPathInfo()).thenReturn("/person");
        when(httpRequest.getMethod()).thenReturn("PUT");

        String controllerPackage = "co.sepulveda.pongee.fake.data.controllers";
        List<Object> objectControllers = null;

        ControllerExecutor controllerExecutor = new ControllerExecutor(controllerPackage, objectControllers);
        controllerExecutor.execute(httpRequest, httpResponse);
        verify(httpResponse).setStatus(404);
    }

    @Test
    public void shouldExecuteMethodFromController() throws IOException {
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getPathInfo()).thenReturn("/person");
        when(httpRequest.getMethod()).thenReturn("GET");
        when(httpRequest.getCookies()).thenReturn(new Cookie[0]);

        String controllerPackage = "co.sepulveda.pongee.fake.data.controllers";
        List<Object> objectControllers = null;

        ControllerExecutor controllerExecutor = new ControllerExecutor(controllerPackage, objectControllers);
        controllerExecutor.execute(httpRequest, httpResponse);
        verify(httpResponse).setStatus(200);
        verify(httpResponse).setHeader(eq("x-custom-header"), eq("custom.value"));
    }
}
