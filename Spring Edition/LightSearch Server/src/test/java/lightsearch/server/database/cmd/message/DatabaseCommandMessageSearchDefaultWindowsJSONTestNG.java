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

package lightsearch.server.database.cmd.message;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
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
public class DatabaseCommandMessageSearchDefaultWindowsJSONTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private DatabaseCommandMessageProducer dbCmdMsgProducer;
    @Autowired private ClientCommandProducer cmdProducer;

    private DatabaseCommandMessage dbCmdMsg;

    @BeforeClass
    public void setUpClass() {
        ClientCommandDTO cmdDTO = new ClientCommandDTO();
        cmdDTO.setCommand("search");
        cmdDTO.setIMEI("111111111111111");
        cmdDTO.setBarcode("225050000");
        cmdDTO.setSklad("Sklad 1");
        cmdDTO.setTK("null");

        dbCmdMsg = dbCmdMsgProducer.getDatabaseCommandMessageSearchDefaultWindowsJSONInstance(
                cmdProducer.getClientCommandDefaultInstance(cmdDTO));
    }

    @Test
    public void message() {
        testBegin("DatabaseCommandMessageSearchDefaultWindowsJSON", "message()");

        String message = dbCmdMsg.message();
        assertNotNull(message, "Message is null!");
        assertFalse(message.isEmpty(), "Message is empty!");
        System.out.println("Search message: " + message);

        testEnd("DatabaseCommandMessageSearchDefaultWindowsJSON", "message()");
    }
}
