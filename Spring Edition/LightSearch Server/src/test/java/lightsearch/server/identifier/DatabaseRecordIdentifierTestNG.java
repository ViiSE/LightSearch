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
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class DatabaseRecordIdentifierTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private DatabaseRecordIdentifierProducer databaseRecordIdentifierProducer;

    private DatabaseRecordIdentifier databaseRecordIdentifier;

    @BeforeClass
    public void setUpClass() {
        databaseRecordIdentifier = databaseRecordIdentifierProducer.getDatabaseRecordIdentifierDefaultInstance();
    }

    @Test
    public void next() {
        testBegin("DatabaseRecordIdentifier", "next()");

        long ident = databaseRecordIdentifier.databaseRecordIdentifier();
        System.out.println("ident BEFORE: " + ident);

        long next = databaseRecordIdentifier.next();
        System.out.println("next: " + next);

        assertEquals(databaseRecordIdentifier.databaseRecordIdentifier(), next, "next and current db value is not equals!");

        System.out.println("ident AFTER: " + databaseRecordIdentifier.databaseRecordIdentifier());

        testEnd("DatabaseRecordIdentifier", "next()");
    }
}
