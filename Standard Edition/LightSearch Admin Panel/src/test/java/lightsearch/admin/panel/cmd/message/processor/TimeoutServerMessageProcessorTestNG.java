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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

import lightsearch.admin.panel.cmd.admin.processor.TestServer;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
import lightsearch.admin.panel.cmd.message.MessageCommandEnum;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminCommandDAOInit;
import lightsearch.admin.panel.data.AdminDAO;
import lightsearch.admin.panel.data.AdminDAOInit;
import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.MessageCommandDTO;
import lightsearch.admin.panel.data.MessageCommandDTOInit;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import lightsearch.admin.panel.exception.MessageRecipientException;
import lightsearch.admin.panel.exception.MessageSenderException;
import lightsearch.admin.panel.exception.SocketException;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageRecipientInit;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import lightsearch.admin.panel.socket.SocketCreator;
import lightsearch.admin.panel.socket.SocketCreatorInit;
import static org.testng.Assert.*;

import org.testng.annotations.*;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class TimeoutServerMessageProcessorTestNG {
    
    private final String TIMEOUT_SERVER = MessageCommandEnum.TIMEOUT_SERVER.stringValue();
    
    private Map<String, Function<AdminCommandDAO, CommandResult>> msgCmdHolder;
    
    @BeforeClass
    @Parameters({"ip", "port", "toutClMessage"})
    public void setUpMethod(String ip, int port, String answerMessage) {
        TestServer.closeServer = false;
        Thread testServerTh = new Thread(new TestServer(port, answerMessage));
        testServerTh.start();

        MessageCommandCreator msgCmdCreator = DataProviderCreator.createDataProvider(MessageCommandCreator.class, ip, port);
        assertNotNull(msgCmdCreator, "MessageCommandCreator is null!");

        msgCmdHolder = msgCmdCreator.createMessageCommandHolder();
        assertNotNull(msgCmdHolder, "MessageCommandHolder is null!");
    }
    
    @Test
    @Parameters({"adminName", "serverTimeout"})
    public void apply(String adminName, String serverTimeout) {
        testBegin("TimeoutServerMessageProcessor", "apply()");
        
        assertNotNull(adminName, "Name is null!");
        assertFalse(adminName.isEmpty(), "Name is null!");
        
        assertNotNull(serverTimeout, "ServerTimeout name is null!");
        assertFalse(serverTimeout.isEmpty(), "ServerTimeout name is null!");
        
        Function<AdminCommandDAO, CommandResult> processor = msgCmdHolder.get(TIMEOUT_SERVER);
        assertNotNull(processor, "TimeoutServerMessageProcessor is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        admCmdDAO.setName(adminName);
        assertNotNull(admCmdDAO.name(), "AdminCommandDAO: name is null!");
                
        admCmdDAO.setServerTimeout(serverTimeout);
        assertNotNull(admCmdDAO.serverTimeout(), "AdminCommandDAO: serverTimeout is null!");
        
        CommandResult cmdRes = processor.apply(admCmdDAO);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        String nameRes = cmdRes.name();
        assertNotNull(nameRes, "CommandResult: name is null!");
        assertFalse(nameRes.isEmpty(), "CommandResult: name is null!");
        
        String isDone = cmdRes.isDone();
        assertNotNull(isDone, "CommandResult: isDone is null!");
        assertFalse(isDone.isEmpty(), "CommandResult: isDone is null!");
        
        String message = cmdRes.message();
        assertNotNull(message, "CommandResult: message is null!");
        assertFalse(message.isEmpty(), "CommandResult: message is null!");
        
        System.out.println("CommandResult: name " + nameRes);
        System.out.println("CommandResult: isDone " + isDone);
        System.out.println("CommandResult: message " + message);
        
        testEnd("TimeoutServerMessageProcessor", "apply()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.closeServer = true;
    }
}
