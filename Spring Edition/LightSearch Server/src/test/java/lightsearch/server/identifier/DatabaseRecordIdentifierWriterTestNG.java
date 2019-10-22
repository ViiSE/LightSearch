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
import lightsearch.server.exception.IdentifierException;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierWriterProducer;
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
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class DatabaseRecordIdentifierWriterTestNG extends AbstractTestNGSpringContextTests {

    @Mock
    private LightSearchServerService serverService;

    @Autowired private DatabaseRecordIdentifierWriterProducer identifierWriterProducer;
    @Autowired private CurrentServerDirectory serverDirectory;

    private DatabaseRecordIdentifierWriter identifierWriter;

    @BeforeClass
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
        when(serverService.currentDirectory()).thenReturn(serverDirectory.currentDirectory() + getResourcesFilesPath());
        identifierWriter = identifierWriterProducer.getDatabaseRecordIdentifierWriterDefaultInstance(serverService);
    }

    @Test
    public void write() {
        testBegin("DatabaseRecordIdentifierWriter", "write()");

        try {
            long writeValue = 544;
            identifierWriter.write(544);
            assertFalse(writeValue < 0, "Write value is less than 0!");
            System.out.println("Write value: " + writeValue);
            System.out.println("Write is done!");
        } catch (IdentifierException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseRecordIdentifierWriter", "write()");
    }
}
