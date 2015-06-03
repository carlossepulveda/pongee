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

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Carlos Sepulveda
 */
public class MimeTypeTest {

    @Test
    public void shouldReturnValidMiMeType() {
        File file = new File("/some/path/file.gif");
        Assert.assertEquals("image/gif", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.jpg");
        Assert.assertEquals("image/jpeg", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.jpeg");
        Assert.assertEquals("image/jpeg", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.jpe");
        Assert.assertEquals("image/jpeg", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.js");
        Assert.assertEquals("application/x-javascript", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.pdf");
        Assert.assertEquals("application/pdf", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.png");
        Assert.assertEquals("image/png", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.html");
        Assert.assertEquals("text/html", MimeType.getMiMeTypeFromFile(file));

        file = new File("/some/path/file.css");
        Assert.assertEquals("text/css", MimeType.getMiMeTypeFromFile(file));
    }

    @Test
    public void shouldReturnDefaultMiMeTypeIfExtensionIsUnknown() {
        File file = new File("/some/path/file.unknown_extension");
        Assert.assertEquals("text/plain", MimeType.getMiMeTypeFromFile(file));
    }
}
