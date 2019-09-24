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

import java.util.Map;
import java.util.function.Function;

import test.TestServer;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandEnum;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminCommandDAOInit;

import static org.testng.Assert.*;

import org.testng.annotations.*;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BlacklistMessageProcessorTestNG {
    
    private final String BLACKLIST = MessageCommandEnum.BLACKLIST.stringValue();
    
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
    @Parameters({"ip", "port", "blMessage"})
    public void setUpMethod(String ip, int port, String answerMessage) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(answerMessage);

        MessageCommandCreator msgCmdCreator = DataProviderCreator.createDataProvider(MessageCommandCreator.class, ip, port);
        assertNotNull(msgCmdCreator, "MessageCommandCreator is null!");

        msgCmdHolder = msgCmdCreator.createMessageCommandHolder();
        assertNotNull(msgCmdHolder, "MessageCommandHolder is null!");

    }
    
    @Test
    @Parameters({"adminName"})
    public void apply(String adminName) {
        testBegin("BlacklistMessageProcessor", "apply()");

        assertNotNull(adminName, "AdminName is null!");
        assertFalse(adminName.isEmpty(), "AdminName is null!");
        
        Function<AdminCommandDAO, CommandResult> processor = msgCmdHolder.get(BLACKLIST);
        assertNotNull(processor, "BlacklistMessageProcessor is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        admCmdDAO.setName(adminName);
        assertNotNull(admCmdDAO.name(), "AdminCommandDAO: name is null!");
                
        CommandResult cmdRes = processor.apply(admCmdDAO);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        String name = cmdRes.name();
        assertNotNull(name, "CommandResult: name is null!");
        assertFalse(name.isEmpty(), "CommandResult: name is null!");
        
        String isDone = cmdRes.isDone();
        assertNotNull(isDone, "CommandResult: isDone is null!");
        assertFalse(isDone.isEmpty(), "CommandResult: isDone is null!");
        
        Object data = cmdRes.data();
        assertNotNull(data, "CommandResult: data is null!");
        
        System.out.println("CommandResult: name " + name);
        System.out.println("CommandResult: isDone " + isDone);
        System.out.println("CommandResult: data " + data);
        
        testEnd("BlacklistMessageProcessor", "apply()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.closeClient = true;
    }
}
