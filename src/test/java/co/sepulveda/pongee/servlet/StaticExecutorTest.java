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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author carlossepulveda
 */
public class StaticExecutorTest {

    @Test
    public void shouldRenderStaticFile() throws IOException {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getPathInfo()).thenReturn("/static/staticFile.txt");

        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        StaticExecutor staticExecutor = new StaticExecutor();
        staticExecutor.execute(httpRequest, httpResponse);
        verify(httpResponse).setStatus(200);
    }

    @Test
    public void shouldNotRenderFileNotExisting() throws IOException {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getPathInfo()).thenReturn("/static/no-existing.txt");

        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        StaticExecutor staticExecutor = new StaticExecutor();
        staticExecutor.execute(httpRequest, httpResponse);
        verify(httpResponse).setStatus(404);
    }
}
