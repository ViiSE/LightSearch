/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.properties;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.PropertyDTO;
import lightsearch.server.entity.Property;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.producer.entity.PropertyProducer;
import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import lightsearch.server.producer.properties.PropertiesLocalChangerProducer;
import lightsearch.server.producer.properties.PropertiesReaderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.ResourcesFilesPath;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class PropertiesLocalChangerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private PropertiesLocalChangerProducer propertiesLocalChangerProducer;
    @Autowired private PropertiesReaderProducer propertiesReaderProducer;
    @Autowired private PropertyProducer propertyProducer;
    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;

    private List<String> beforeChangedPropsList;
    private PropertiesLocalChanger<List<String>> propertiesLocalChanger;

    @BeforeClass
    public void setUpClass() throws ReaderException {
        Property<String> property = propertyProducer.getSimplePropertyInstance(
                "lightsearch.server.test.example-property", "hello test");

        PropertiesReader<List<String>> propsReader = propertiesReaderProducer.getPropertiesListStringReaderInstance(
                currentServerDirectoryProducer
                        .getCurrentServerDirectoryFromFileInstance(osDetectorProducer.getOsDetectorDefaultInstance())
                        .name() +
                        ResourcesFilesPath.getResourcesFilesPath() + File.separator + "config/application.properties");

        propertiesLocalChanger = propertiesLocalChangerProducer.getPropertiesLocalChangerDefaultInstance(property, propsReader);

        beforeChangedPropsList = propsReader.read();
    }

    @Test
    public void read() {
        testBegin("PropertiesLocalChanger", "getChangedProperties()");

        try {
            System.out.println("(BEFORE) Properties list:");
            System.out.println("------------------------------------------------------------------");
            beforeChangedPropsList.forEach(System.out::println);
            System.out.println("------------------------------------------------------------------");

            List<String> afterChangedPropsList = propertiesLocalChanger.getChangedProperties();
            System.out.println("(AFTER) Properties list:");
            System.out.println("------------------------------------------------------------------");
            afterChangedPropsList.forEach(System.out::println);
            System.out.println("------------------------------------------------------------------");
        } catch (PropertiesException ex) {
            catchMessage(ex);
        }

        testEnd("PropertiesLocalChanger", "getChangedProperties()");
    }
}
