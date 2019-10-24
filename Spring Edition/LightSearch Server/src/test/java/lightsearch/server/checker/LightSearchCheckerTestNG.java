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

package lightsearch.server.checker;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.pojo.Product;
import lightsearch.server.producer.checker.LightSearchCheckerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class LightSearchCheckerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private LightSearchCheckerProducer checkerProducer;

    private String[] testStringsTrue;
    private Object[] testObjectsTrue;
    private String[] testStringsFalse;
    private Object[] testObjectsFalse;
    private LightSearchChecker checker;

    @BeforeClass
    public void setUpClass() {
        testStringsTrue = new String[] {
                "String 1",
                "",
                "String 3"
        };

        testObjectsTrue = new Object[] {
                new Product("Sklad 1", "111111", "Item 1", "10", "20", "pcs."),
                null,
                "String"
        };

        testStringsFalse = new String[] {
                "String 1",
                "String 2",
                "String 3"
        };

        testObjectsFalse = new Object[] {
            new Product("Sklad 1", "111111", "Item 1", "10", "20", "pcs."),
            new Product("Sklad 2", "222222", "Item 2", "20", "10","pcs."),
            "String"
        };

        checker = checkerProducer.getLightSearchCheckerDefaultInstance();
    }

    @Test
    public void isEmpty_false() {
        testBegin("LightSearchChecker", "isEmpty()");

        assertFalse(checker.isEmpty(testStringsFalse), "isEmpty result must be false!");
        System.out.println("check: " + checker.isEmpty(testStringsFalse));

        testEnd("LightSearchChecker", "isEmpty()");
    }

    @Test
    public void isEmpty_true() {
        testBegin("LightSearchChecker", "isEmpty()");

        assertTrue(checker.isEmpty(testStringsTrue), "isEmpty result must be true!");
        System.out.println("check: " + checker.isEmpty(testStringsTrue));

        testEnd("LightSearchChecker", "isEmpty()");
    }

    @Test
    public void isNull_false() {
        testBegin("LightSearchChecker", "isNull()");

        assertFalse(checker.isNull(testObjectsFalse), "isNull result must be false!");
        System.out.println("check: " + checker.isNull(testObjectsFalse));

        testEnd("LightSearchChecker", "isNull()");
    }

    @Test
    public void isNull_true() {
        testBegin("LightSearchChecker", "isNull()");

        assertTrue(checker.isNull(testObjectsTrue), "isNull result must be true!");
        System.out.println("check: " + checker.isNull(testObjectsTrue));

        testEnd("LightSearchChecker", "isNull()");
    }
}
