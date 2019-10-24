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

package lightsearch.server.log;

import lightsearch.server.LightSearchServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class LogDirectoryTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private LogDirectory logDirectory;

    @Test
    public void logDirectory() {
        testBegin("LogDirectory", "logDirectory()");

        String logDir = logDirectory.logDirectory();
        assertNotNull(logDir, "Log directory is null!");
        assertFalse(logDir.isEmpty(), "Log directory is empty!");

        System.out.println("Log directory: " + logDir);

        testEnd("LogDirectory", "logDirectory()");
    }
}
