/*
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.admin.panel.cmd.message.processor;

import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandEnum;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminCommandDAOInit;
import org.testng.annotations.*;
import test.TestServer;
import test.data.DataProviderCreator;

import java.util.Map;
import java.util.function.Function;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class RestartMessageProcessorTestNG {
    
    private final String RESTART = MessageCommandEnum.RESTART.stringValue();
    
    private Map<String, Function<AdminCommandDAO, CommandResult>> msgCmdHolder;

    @BeforeTest
    @Parameters({"ip", "port"})
    public void setUpTest(String ip, int port) {
        if(!TestServer.serverOn) {
            Thread testServerTh = new Thread(new TestServer(port));
            testServerTh.start();
        }
    }

    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpMethod(String ip, int port) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(null);
        TestServer.setSimpleMode(true);

        MessageCommandCreator msgCmdCreator = DataProviderCreator.createDataProvider(MessageCommandCreator.class, ip, port);
        assertNotNull(msgCmdCreator, "MessageCommandCreator is null!");

        msgCmdHolder = msgCmdCreator.createMessageCommandHolder();
        assertNotNull(msgCmdHolder, "MessageCommandHolder is null!");
    }
    
    @Test
    @Parameters({"adminName"})
    public void apply(String name) {
        testBegin("RestartMessageProcessor", "apply()");

        assertNotNull(name, "Name is null!");
        assertFalse(name.isEmpty(), "Name is null!");
        
        Function<AdminCommandDAO, CommandResult> processor = msgCmdHolder.get(RESTART);
        assertNotNull(processor, "RestartMessageProcessor is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        admCmdDAO.setName(name);
        assertNotNull(admCmdDAO.name(), "AdminCommandDAO: name is null!");
                
        CommandResult cmdRes = processor.apply(admCmdDAO);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        String message = cmdRes.message();
        assertNotNull(message, "CommandResult: message is null!");
        assertFalse(message.isEmpty(), "CommandResult: message is null!");
        
        System.out.println("CommandResult: message " + message);
        
        testEnd("RestartMessageProcessor", "apply()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }
}
