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

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author carlossepulveda
 */
public class HttpMethodTest {

    @Test
    public void shouldReturnHttpMethod() {
        Assert.assertEquals(HttpMethod._GET, HttpMethod.getMethod("get"));
        Assert.assertEquals(HttpMethod._GET, HttpMethod.getMethod("GET"));
        Assert.assertEquals(HttpMethod._GET, HttpMethod.getMethod("gEt"));

        Assert.assertEquals(HttpMethod._POST, HttpMethod.getMethod("post"));
        Assert.assertEquals(HttpMethod._POST, HttpMethod.getMethod("POST"));
        Assert.assertEquals(HttpMethod._POST, HttpMethod.getMethod("poSt"));

        Assert.assertEquals(HttpMethod._PUT, HttpMethod.getMethod("put"));
        Assert.assertEquals(HttpMethod._PUT, HttpMethod.getMethod("PUT"));
        Assert.assertEquals(HttpMethod._PUT, HttpMethod.getMethod("pUt"));

        Assert.assertEquals(HttpMethod._DELETE, HttpMethod.getMethod("delete"));
        Assert.assertEquals(HttpMethod._DELETE, HttpMethod.getMethod("DELETE"));
        Assert.assertEquals(HttpMethod._DELETE, HttpMethod.getMethod("deLete"));
    }

    @Test
    public void shouldReturNullIfItsAnInvalidMethod() {
        Assert.assertNull(HttpMethod.getMethod("some"));
        Assert.assertNull(HttpMethod.getMethod("invalid"));
        Assert.assertNull(HttpMethod.getMethod(null));
    }
}
