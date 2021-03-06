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
package co.sepulveda.pongee.fake.data.controllers;

import co.sepulveda.pongee.annotations.GET;
import co.sepulveda.pongee.annotations.Resource;
import co.sepulveda.pongee.servlet.http.Request;
import co.sepulveda.pongee.servlet.http.Response;
import javax.servlet.http.Cookie;

/**
 *
 * @author carlossepulveda
 */
@Resource(name="/person")
public class PersonController {

    @GET
    public Response index(Request request) {
        Response response = new Response();
        response.setStatus(200);
        response.addHeader("x-custom-header", "custom.value");
        Cookie cookieTest = request.getCookie("test-cookie");
        if (cookieTest != null) {
            String responseBody = request.getBodyAsString() + " response " + cookieTest.getValue();
            response.setBody(responseBody);
        }

        return response;
    }
}
