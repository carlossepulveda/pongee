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
import co.sepulveda.pongee.servlet.ServletHandler;
import co.sepulveda.pongee.servlet.http.HttpMethod;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author carlossepulveda
 */
public class MockServletHandler extends ServletHandler {

    public MockServletHandler(Configuration config) {
        super(config);
    }

    public MockResponse execute(MockRequest request) throws IOException, ServletException {
        MockResponse mockResponse = new MockResponse();
        HttpMethod method = HttpMethod.getMethod(request.getHttpRequest().getMethod());
        MockResponse response = new MockResponse();

        if (method.equals(HttpMethod._GET)) {
            mockResponse = get(request, response);
        }

        if (method.equals(HttpMethod._POST)) {
            mockResponse = post(request, response);
        }

        if (method.equals(HttpMethod._PUT)) {
            mockResponse = put(request, response);
        }

        if (method.equals(HttpMethod._DELETE)) {
            mockResponse = delete(request, response);
        }

        if (method.equals(HttpMethod._HEAD)) {
            mockResponse = head(request, response);
        }

        if (method.equals(HttpMethod._TRACE)) {
            mockResponse = trace(request, response);
        }

        if (method.equals(HttpMethod._OPTIONS)) {
            mockResponse = options(request, response);
        }

        return mockResponse;
    }

    public MockResponse get(MockRequest req, MockResponse resp) throws IOException {
        super.doGet(req.getHttpRequest(), resp.getHttpResponse());
        return resp;
    }

    public MockResponse post(MockRequest req, MockResponse resp) throws IOException {
        super.doPost(req.getHttpRequest(), resp.getHttpResponse());
        return resp;
    }

    public MockResponse put(MockRequest req, MockResponse resp) throws IOException {
        super.doPut(req.getHttpRequest(), resp.getHttpResponse());
        return resp;
    }

    public MockResponse delete(MockRequest req, MockResponse resp) throws IOException {
        super.doDelete(req.getHttpRequest(), resp.getHttpResponse());
        return resp;
    }

    public MockResponse options(MockRequest req, MockResponse resp) throws IOException, ServletException {
        super.doOptions(req.getHttpRequest(), resp.getHttpResponse());
        return resp;
    }

    public MockResponse head(MockRequest req, MockResponse resp) throws IOException, ServletException {
        super.doHead(req.getHttpRequest(), resp.getHttpResponse());
        return resp;
    }

    public MockResponse trace(MockRequest req, MockResponse resp) throws IOException, ServletException {
        super.doTrace(req.getHttpRequest(), resp.getHttpResponse());
        return resp;
    }
}
