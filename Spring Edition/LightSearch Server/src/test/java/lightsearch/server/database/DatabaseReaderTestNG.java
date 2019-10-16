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

package lightsearch.server.database;

import lightsearch.server.LightSearchServer;
import lightsearch.server.exception.DatabaseReaderException;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.database.DatabaseReaderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class DatabaseReaderTestNG extends AbstractTestNGSpringContextTests {

    private long lsCode;

    @Autowired private ClientCommandProducer clientCommandProducer;
    @Autowired private DatabaseReaderProducer databaseReaderProducer;

    @BeforeClass
    public void setUpMethod() {
        lsCode = 3;
    }

    @Test
    public void read() {
        testBegin("DatabaseReaderTestNG", "read()");

        try {
            DatabaseReader reader = databaseReaderProducer.getDatabaseReaderDefaultInstance(lsCode);
            String result = reader.read();
            System.out.println("Result: " + result);
        } catch (DatabaseReaderException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseReaderTestNG", "read()");
    }
}
