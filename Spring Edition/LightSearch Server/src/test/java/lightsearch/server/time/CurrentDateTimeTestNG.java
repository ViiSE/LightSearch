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

package lightsearch.server.time;

import lightsearch.server.LightSearchServer;
import lightsearch.server.producer.time.CurrentDateTimeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class CurrentDateTimeTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private CurrentDateTimeProducer currentDateTimeProducer;

    private CurrentDateTime currentDateTime;

    @BeforeClass
    public void setUpClass() {
        currentDateTime = currentDateTimeProducer.getCurrentDateTimeDefaultInstance();
    }

    @Test
    public void dateLog() {
        testBegin("CurrentDateTime", "dateLog()");

        String dateLog = currentDateTime.dateLog();
        assertNotNull(dateLog, "Date log is null!");
        assertFalse(dateLog.isEmpty(), "Date log is empty()");
        System.out.println("Date log: " + dateLog);

        testEnd("CurrentDateTime", "dateLog()");
    }

    @Test
    public void dateTimeInStandardFormat() {
        testBegin("CurrentDateTime", "dateTimeInStandardFormat()");

        String dateTimeInStandardFormat = currentDateTime.dateTimeInStandardFormat();
        assertNotNull(dateTimeInStandardFormat, "Date time in standard format is null!");
        assertFalse(dateTimeInStandardFormat.isEmpty(), "Date time in standard format is empty()");
        System.out.println("Date time in standard format: " + dateTimeInStandardFormat);

        testEnd("CurrentDateTime", "dateTimeInStandardFormat()");
    }

    @Test
    public void dateTimeWithDot() {
        testBegin("CurrentDateTime", "dateTimeWithDot()");

        String dateTimeWithDot = currentDateTime.dateTimeWithDot();
        assertNotNull(dateTimeWithDot, "Date time with dot is null!");
        assertFalse(dateTimeWithDot.isEmpty(), "Date time with dot is empty()");
        System.out.println("Date time with dot: " + dateTimeWithDot);

        testEnd("CurrentDateTime", "dateTimeWithDot()");
    }
}
