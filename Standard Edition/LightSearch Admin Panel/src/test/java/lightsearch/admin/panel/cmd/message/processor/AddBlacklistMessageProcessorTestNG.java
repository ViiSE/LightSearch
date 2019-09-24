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
public class AddBlacklistMessageProcessorTestNG {

    private final String ADD_BLACKLIST = MessageCommandEnum.ADD_BLACKLIST.stringValue();
    
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
    @Parameters({"ip", "port", "addBlMessage"})
    public void setUpMethod(String ip, int port, String answerMessage) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(answerMessage);
        
        MessageCommandCreator msgCmdCreator = DataProviderCreator.createDataProvider(MessageCommandCreator.class, ip, port);
        assertNotNull(msgCmdCreator, "MessageCommandCreator is null!");

        msgCmdHolder = msgCmdCreator.createMessageCommandHolder();
        assertNotNull(msgCmdHolder, "MessageCommandHolder is null!");
    }
    
    @Test
    @Parameters({"adminName", "IMEI"})
    public void apply(String adminName, String IMEI) {
        testBegin("AddBlacklistMessageProcessor", "apply()");

        assertNotNull(adminName, "AdminName is null!");
        assertFalse(adminName.isEmpty(), "AdminName is null!");
        
        assertNotNull(IMEI, "IMEI is null!");
        assertFalse(IMEI.isEmpty(), "IMEI is null!");
        
        Function<AdminCommandDAO, CommandResult> processor = msgCmdHolder.get(ADD_BLACKLIST);
        assertNotNull(processor, "AddBlacklistMessageProcessor is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        admCmdDAO.setName(adminName);
        assertNotNull(admCmdDAO.name(), "AdminCommandDAO: name is null!");
                
        admCmdDAO.setIMEI(IMEI);
        assertNotNull(admCmdDAO.IMEI(), "AdminCommandDAO: IMEI is null!");
        
        CommandResult cmdRes = processor.apply(admCmdDAO);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        String name = cmdRes.name();
        assertNotNull(name, "CommandResult: name is null!");
        assertFalse(name.isEmpty(), "CommandResult: name is null!");
        
        String isDone = cmdRes.isDone();
        assertNotNull(isDone, "CommandResult: isDone is null!");
        assertFalse(isDone.isEmpty(), "CommandResult: isDone is null!");
        
        String message = cmdRes.message();
        assertNotNull(message, "CommandResult: message is null!");
        assertFalse(message.isEmpty(), "CommandResult: message is null!");
        
        System.out.println("CommandResult: name " + name);
        System.out.println("CommandResult: isDone " + isDone);
        System.out.println("CommandResult: message " + message);
        
        testEnd("AddBlacklistMessageProcessor", "apply()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.closeClient = true;
    }
}
