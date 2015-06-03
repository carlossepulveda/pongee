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
package co.sepulveda.pongee.util;

import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Carlos Sepulveda
 */
public class PathParserTest {

    @Test
    public void shouldMatchPaths() {
        boolean response = PathParser.match("/acme/employees/3", "/:company/employees/:id/");
        Assert.assertTrue(response);

        response = PathParser.match("/", "");
        Assert.assertTrue(response);

        response = PathParser.match("/path", "/path/");
        Assert.assertTrue(response);

        response = PathParser.match("path", "/path/");
        Assert.assertTrue(response);
    }

    @Test
    public void shouldParseURLData() {
        Map<String, String> data = PathParser.getData("/acme/employees/3/", "/:company/employees/:id/");
        Assert.assertNotNull(data);
        Assert.assertEquals(data.isEmpty(), false);
        Assert.assertEquals(2, data.size());
        Assert.assertEquals("acme", data.get("company"));
        Assert.assertEquals("3", data.get("id"));
    }

    @Test
    public void shouldReturnRegexPathArray() {
        String[] array = PathParser.getRegexPathAsArray("/company/:idCompany/employees/:idEmployee");
        Assert.assertNotNull(array);
        Assert.assertEquals(4, array.length);
        Assert.assertEquals("::", array[1]);
        Assert.assertEquals("::", array[3]);
    }
}
