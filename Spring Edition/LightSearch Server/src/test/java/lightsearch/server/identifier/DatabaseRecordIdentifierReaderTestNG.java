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

package lightsearch.server.identifier;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierReaderProducer;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class DatabaseRecordIdentifierReaderTestNG extends AbstractTestNGSpringContextTests {

    @Mock
    private LightSearchServerService serverService;

    @Autowired private DatabaseRecordIdentifierReaderProducer identifierReaderProducer;
    @Autowired private CurrentServerDirectory serverDirectory;

    private DatabaseRecordIdentifierReader identifierReader;

    @BeforeClass
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
        when(serverService.currentDirectory()).thenReturn(serverDirectory.currentDirectory() + getResourcesFilesPath());
        identifierReader = identifierReaderProducer.getDatabaseRecordIdentifierReaderDefaultInstance(serverService);
    }

    @Test
    public void read() {
        testBegin("DatabaseRecordIdentifierReader", "read()");

        long readValue = identifierReader.read();
        assertFalse(readValue < 0, "Value is less than 0!");
        System.out.println("Read value: " + readValue);

        testEnd("DatabaseRecordIdentifierReader", "read()");
    }
}
