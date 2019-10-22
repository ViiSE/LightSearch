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
import lightsearch.server.producer.time.DateTimeComparatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class DateTimeComparatorTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private DateTimeComparatorProducer dateTimeComparatorProducer;

    private DateTimeComparator dateTimeComparator;

    @BeforeClass
    public void setUpClass() {
        dateTimeComparator = dateTimeComparatorProducer.getDateTimeComparatorDefaultInstance(
                CurrentDateTimePattern.dateTimeInStandardFormWithMs());
    }

    @Test
    public void isAfter_true() {
        testBegin("DateTimeComparator", "isAfter()");

        LocalDateTime afterDate = LocalDateTime.now().plusDays(1);
        assertTrue(dateTimeComparator.isAfter(afterDate, LocalDateTime.now()),
                "isAfter must be true!");
        System.out.println("isAfter(" + afterDate + "," + LocalDateTime.now() + "):" +
                dateTimeComparator.isAfter(afterDate, LocalDateTime.now()));

        testEnd("DateTimeComparator", "isAfter()");
    }

    @Test
    public void isAfter_false() {
        testBegin("DateTimeComparator", "isAfter()");

        LocalDateTime beforeDate = LocalDateTime.now().minusDays(1);
        assertFalse(dateTimeComparator.isAfter(beforeDate, LocalDateTime.now()),
                "isAfter must be false!");
        System.out.println("isAfter(" + beforeDate + "," + LocalDateTime.now() + "):" +
                dateTimeComparator.isAfter(beforeDate, LocalDateTime.now()));

        testEnd("DateTimeComparator", "isAfter()");
    }

    @Test
    public void isBefore_true() {
        testBegin("DateTimeComparator", "isBefore()");

        LocalDateTime beforeDate = LocalDateTime.now().minusDays(1);
        assertTrue(dateTimeComparator.isBefore(beforeDate, LocalDateTime.now()),
                "isBefore must be true!");
        System.out.println("isBefore(" + beforeDate + "," + LocalDateTime.now() + "):" +
                dateTimeComparator.isBefore(beforeDate, LocalDateTime.now()));

        testEnd("DateTimeComparator", "isBefore()");
    }

    @Test
    public void isBefore_false() {
        testBegin("DateTimeComparator", "isBefore()");

        LocalDateTime afterDate = LocalDateTime.now().plusDays(1);
        assertFalse(dateTimeComparator.isBefore(afterDate, LocalDateTime.now()),
                "isAfter must be false!");
        System.out.println("isBefore(" + afterDate + "," + LocalDateTime.now() + "):" +
                dateTimeComparator.isBefore(afterDate, LocalDateTime.now()));

        testEnd("DateTimeComparator", "isBefore()");
    }
}
