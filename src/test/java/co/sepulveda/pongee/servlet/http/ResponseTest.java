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

import co.sepulveda.pongee.fake.data.anyclass.SchoolPresenter;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author carlossepulveda
 */
public class ResponseTest {

    @Test
    public void shouldReturnFormattedBodyAsJson() {
        SchoolPresenter school = getSchool();
        Response response = new Response();
        response.setBody(school);

        String formattedBody = response.getFormattedBody();
        Assert.assertEquals("{\"address\":\"street1\",\"name\":\"Calasanz\"}", formattedBody);
    }

    @Test
    public void shouldReturnFormattedBodyAsText() {
        Response response = new Response();
        response.setBody("some text");

        String formattedBody = response.getFormattedBody();
        Assert.assertEquals("some text", formattedBody);
    }

    private SchoolPresenter getSchool() {
        SchoolPresenter school = new SchoolPresenter();
        school.setAddress("street1");
        school.setName("Calasanz");

        return school;
    }
}
