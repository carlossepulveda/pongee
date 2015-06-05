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

/**
 *
 * @author carlossepulveda
 */
public class MockServer {

    private final MockServletHandler servletHandler;

    private MockServer(Configuration config) {
        servletHandler = new MockServletHandler(config);
    }

    public MockResponse run(MockRequest request) throws IOException, ServletException {
        return servletHandler.execute(request);
    }

    public static MockServer build(Configuration conf) {
        return new MockServer(conf);
    }
}
