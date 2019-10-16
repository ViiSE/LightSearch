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
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.exception.DatabaseWriterException;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import lightsearch.server.producer.database.DatabaseWriterProducer;
import lightsearch.server.producer.time.CurrentDateTimeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class DatabaseWriterTestNG extends AbstractTestNGSpringContextTests {

    private long lsCode;
    private String dateTime;
    private String command;

    @Autowired private DatabaseCommandMessageProducer messageProducer;
    @Autowired private CurrentDateTimeProducer currentDateTimeProducer;
    @Autowired private ClientCommandProducer clientCommandProducer;
    @Autowired private DatabaseWriterProducer databaseWriterProducer;

    @BeforeClass
    public void setUpMethod() {
        lsCode = 3;
        dateTime = currentDateTimeProducer.getCurrentDateTimeDefaultInstance().dateTimeInStandardFormat();

        ClientCommandDTO clientCommandDTO = new ClientCommandDTO();
        clientCommandDTO.setCommand("connect");
        clientCommandDTO.setUsername("test");
        clientCommandDTO.setPassword("321");
        clientCommandDTO.setIMEI("111111111111111");
        clientCommandDTO.setIp("192.168.3.215");
        clientCommandDTO.setOs("Windows 10");
        clientCommandDTO.setModel("Lenovo IdeaPad 530s");
        clientCommandDTO.setUserIdentifier("111");

        ClientCommand clientCommand = clientCommandProducer.getClientCommandDefaultInstance(clientCommandDTO);
        command = messageProducer.getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(clientCommand).message();
    }

    @Test
    public void write() {
        testBegin("DatabaseWriterTestNG", "write()");

        try {
            DatabaseWriter writer = databaseWriterProducer.getDatabaseWriterDefaultInstance(lsCode, dateTime, command);
            writer.write();
            System.out.println("Write completed");
        } catch (DatabaseWriterException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseWriterTestNG", "write()");
    }
}
