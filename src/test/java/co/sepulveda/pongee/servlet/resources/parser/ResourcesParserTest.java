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
package co.sepulveda.pongee.servlet.resources.parser;

import co.sepulveda.pongee.fake.data.anyclass.SomeClass;
import co.sepulveda.pongee.servlet.resources.ResourceEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author carlossepulveda
 */
public class ResourcesParserTest {

    @Test
    public void shouldParseControllersAsPackageAndObjects() {
        List<Object> anyObjectsController = getControllersList();
        String controllersPackageName = "co.sepulveda.pongee.fake.data.controllers";

        List<ResourceEntity> resourceEntities = ResourcesParser.parse(controllersPackageName, anyObjectsController);
        Assert.assertNotNull(resourceEntities);
        Assert.assertFalse(resourceEntities.isEmpty());
        Assert.assertEquals(2, resourceEntities.size());
    }

    @Test
    public void shouldParseControllersIfPackageDosentHasClasses() {
        List<Object> anyObjectsController = getControllersList();
        String controllersPackageName = "no.existing.package";

        List<ResourceEntity> resourceEntities = ResourcesParser.parse(controllersPackageName, anyObjectsController);
        Assert.assertNotNull(resourceEntities);
        Assert.assertFalse(resourceEntities.isEmpty());
        Assert.assertEquals(1, resourceEntities.size());
    }

    @Test
    public void shouldParseControllersIfPackageIsNull() {
        List<Object> anyObjectsController = getControllersList();
        String controllersPackageName = null;

        List<ResourceEntity> resourceEntities = ResourcesParser.parse(controllersPackageName, anyObjectsController);
        Assert.assertNotNull(resourceEntities);
        Assert.assertFalse(resourceEntities.isEmpty());
        Assert.assertEquals(1, resourceEntities.size());
    }

    @Test
    public void shouldParseControllersIfObjectsControllerIsEmpty() {
        List<Object> anyObjectsController = Collections.emptyList();
        String controllersPackageName = "co.sepulveda.pongee.fake.data.controllers";

        List<ResourceEntity> resourceEntities = ResourcesParser.parse(controllersPackageName, anyObjectsController);
        Assert.assertNotNull(resourceEntities);
        Assert.assertFalse(resourceEntities.isEmpty());
        Assert.assertEquals(1, resourceEntities.size());
    }

    @Test
    public void shouldParseControllersIfObjectsControllerIsNull() {
        List<Object> anyObjectsController = null;
        String controllersPackageName = "co.sepulveda.pongee.fake.data.controllers";

        List<ResourceEntity> resourceEntities = ResourcesParser.parse(controllersPackageName, anyObjectsController);
        Assert.assertNotNull(resourceEntities);
        Assert.assertFalse(resourceEntities.isEmpty());
        Assert.assertEquals(1, resourceEntities.size());
    }

    @Test
    public void shouldNotFailIfObjectsControllerAndControllersPackageBothAreNull() {
        List<Object> anyObjectsController = null;
        String controllersPackageName = null;

        List<ResourceEntity> resourceEntities = ResourcesParser.parse(controllersPackageName, anyObjectsController);
        Assert.assertNotNull(resourceEntities);
        Assert.assertTrue(resourceEntities.isEmpty());
    }

    private List<Object> getControllersList() {
        List<Object> controllers = new ArrayList();
        controllers.add(new SomeClass());

        return controllers;
    }
}
