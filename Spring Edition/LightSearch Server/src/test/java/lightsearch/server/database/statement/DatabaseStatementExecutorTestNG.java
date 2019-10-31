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

package lightsearch.server.database.statement;

import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.client.ClientCommandEnum;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import lightsearch.server.producer.database.DatabaseStatementExecutorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class DatabaseStatementExecutorTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private DatabaseStatementExecutorProducer dbStatementExecProducer;
    @Autowired private DatabaseCommandMessageProducer dbCmdMsgProducer;
    @Autowired private ClientCommandProducer clCmdProducer;

    private DatabaseStatementExecutor dbStatementExec;

    @BeforeClass
    public void setUpClass() {
        ClientCommandDTO clientCommandDTO = new ClientCommandDTO();
        clientCommandDTO.setCommand(ClientCommandEnum.LOGIN.stringValue());
        clientCommandDTO.setUsername("test");
        clientCommandDTO.setPassword("321");
        clientCommandDTO.setIMEI("111111111111111");
        clientCommandDTO.setIp("127.0.0.1");
        clientCommandDTO.setOs("Windows 10");
        clientCommandDTO.setModel("Lenovo IdeaPad 530s");
        clientCommandDTO.setUserIdentifier("777");

        DatabaseCommandMessage dbCmdMsg = dbCmdMsgProducer
                .getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(
                        clCmdProducer.getClientCommandDefaultInstance(clientCommandDTO));

        dbStatementExec = dbStatementExecProducer.getDatabaseStatementExecutorH2TestInstance(
                1, "2019-10-24 12:00:00.0", dbCmdMsg);
    }

    @Test
    public void exec() {
        testBegin("DatabaseStatementExecutor", "exec()");

        try {
            DatabaseStatementResult dbStatRes = dbStatementExec.exec();
            assertNotNull(dbStatRes, "DatabaseStatementResult is null!");
            String result = dbStatRes.result();
            assertNotNull(result, "Result is null!");
            assertFalse(result.isEmpty(), "Result is empty!");

            System.out.println("Result: " + result);
        } catch (DatabaseStatementExecutorException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseStatementExecutor", "exec()");
    }
}
