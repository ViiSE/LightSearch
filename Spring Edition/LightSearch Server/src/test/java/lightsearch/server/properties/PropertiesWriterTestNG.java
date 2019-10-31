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
import lightsearch.server.data.pojo.Property;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.exception.ReaderException;
import lightsearch.server.exception.WriterException;
import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import lightsearch.server.producer.properties.PropertiesLocalChangerProducer;
import lightsearch.server.producer.properties.PropertiesReaderProducer;
import lightsearch.server.producer.properties.PropertiesWriterProducer;
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
public class PropertiesWriterTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private PropertiesWriterProducer propertiesWriterProducer;
    @Autowired private PropertiesLocalChangerProducer propertiesLocalChangerProducer;
    @Autowired private PropertiesReaderProducer propertiesReaderProducer;
    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;

    private PropertiesWriter propertiesWriter;

    @BeforeClass
    public void setUpClass() throws ReaderException, PropertiesException {
        Map<String, Property> props = new HashMap<>() {{
            put("lightsearch.server.test.example-property", new Property("%s=%s", "hello test"));
        }};

        String propDir = currentServerDirectoryProducer
                .getCurrentServerDirectoryFromFileInstance(osDetectorProducer.getOsDetectorDefaultInstance())
                .currentDirectory() +
                ResourcesFilesPath.getResourcesFilesPath() + File.separator + "config/application.properties";

        PropertiesReader<List<String>> propsReader = propertiesReaderProducer.getPropertiesListStringReaderInstance(propDir);

        PropertiesLocalChanger<List<String>> propsLocalChanger =
                propertiesLocalChangerProducer.getPropertiesLocalChangerDefaultInstance(props, propsReader);

        propertiesWriter = propertiesWriterProducer
                .getPropertiesFileWriterInstance(propDir, propsLocalChanger.getChangedProperties(), false);
    }

    @Test
    public void read() {
        testBegin("PropertiesWriter", "read()");

        try {
            propertiesWriter.write();
            System.out.println("Success");
        } catch (WriterException ex) {
            catchMessage(ex);
        }

        testEnd("PropertiesWriter", "read()");
    }
}
