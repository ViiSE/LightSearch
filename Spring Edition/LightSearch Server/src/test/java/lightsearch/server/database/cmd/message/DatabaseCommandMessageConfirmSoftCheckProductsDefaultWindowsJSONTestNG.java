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
import lightsearch.server.data.pojo.Product;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private DatabaseCommandMessageProducer dbCmdMsgProducer;
    @Autowired private ClientCommandProducer cmdProducer;

    private DatabaseCommandMessage dbCmdMsg;

    @BeforeClass
    public void setUpClass() {
        ClientCommandDTO cmdDTO = new ClientCommandDTO();
        cmdDTO.setCommand("cancel_soft_check");
        cmdDTO.setIMEI("111111111111111");
        cmdDTO.setUserIdentifier("777");
        cmdDTO.setCardCode("22505");
        cmdDTO.setData(new ArrayList<>() {{
            add(new Product("Sklad 1", "111111", "Item 1", "10", "30", "pcs."));
            add(new Product("Sklad 2", "222222", "Item 2", "20", "10", "pcs."));
        }});

        dbCmdMsg = dbCmdMsgProducer.getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance(
                cmdProducer.getClientCommandDefaultInstance(cmdDTO));
    }

    @Test
    public void message() {
        testBegin("DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSON", "message()");

        String message = dbCmdMsg.message();
        assertNotNull(message, "Message is null!");
        assertFalse(message.isEmpty(), "Message is empty!");
        System.out.println("Confirm SoftCheck products message: " + message);

        testEnd("DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSON", "message()");
    }
}
