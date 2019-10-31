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
import lightsearch.server.exception.ReaderException;
import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import lightsearch.server.producer.properties.PropertiesReaderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.ResourcesFilesPath;

import java.io.File;
import java.util.List;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class PropertiesReaderTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private PropertiesReaderProducer propertiesReaderProducer;
    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;

    private PropertiesReader<List<String>> propertiesReader;

    @BeforeClass
    public void setUpClass() {
        propertiesReader = propertiesReaderProducer.getPropertiesListStringReaderInstance(
                currentServerDirectoryProducer
                        .getCurrentServerDirectoryFromFileInstance(osDetectorProducer.getOsDetectorDefaultInstance())
                        .currentDirectory() +
                        ResourcesFilesPath.getResourcesFilesPath() + File.separator + "config/application.properties");
    }

    @Test
    public void read() {
        testBegin("PropertiesReader", "read()");

        try {
            List<String> propertiesList = propertiesReader.read();
            System.out.println("Properties list:");
            System.out.println("------------------------------------------------------------------");
            propertiesList.forEach(System.out::println);
            System.out.println("------------------------------------------------------------------");
        } catch (ReaderException ex) {
            catchMessage(ex);
        }

        testEnd("PropertiesReader", "read()");
    }
}
