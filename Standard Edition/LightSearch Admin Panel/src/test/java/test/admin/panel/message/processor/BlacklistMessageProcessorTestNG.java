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
package test.admin.panel.message.processor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BlacklistMessageProcessorTestNG {
    
    private Thread testServerTh;
    private boolean closeServer = true;
    private final String BLACKLIST = MessageCommandEnum.BLACKLIST.stringValue();
    
    private Map<String, Function<AdminCommandDAO, CommandResult>> msgCmdHolder;
    
    @BeforeTest
    public void setUpMethod() {
        testServerTh = new Thread(new TestServerAddBlacklistProcessor());
        testServerTh.start();
        
        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        assertNotNull("printer", "AdminPanelPrinter is null!");
        
        ScannerConnectionDTOCreator scConnDTOCreator = 
                ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        assertNotNull(scConnDTOCreator, "ScannerConnectionDTOCreator is null!");
        
        ScannerConnectionDTO scConnDTO = scConnDTOCreator.createScannerConnectionDTO();
        assertNotNull(scConnDTO, "ScannerConnectionDTO is null!");
        
        ConnectionDTOCreator connDTOCreator = 
                ConnectionDTOCreatorInit.connectionDTOCreator(printer, scConnDTO);
        assertNotNull(connDTOCreator, "ConnectionDTOCreator is null!");
        
        ConnectionDTO connDTO = connDTOCreator.createConnectionDTO();
        assertNotNull(connDTO, "ConnectionDTO is null!");
        
        SocketCreator admSocketCreator = SocketCreatorInit.socketCreator(connDTO);
        assertNotNull(admSocketCreator, "Socket creator is null!");
        
        try {
            Socket adminSocket = admSocketCreator.createSocket();
            assertNotNull(adminSocket, "Socket is null!");

            AdminDAO adminDAO = AdminDAOInit.adminDAO();
            assertNotNull(adminDAO, "AdminDAO is null!");

            DataStreamCreator dsCreator = 
                    DataStreamCreatorInit.dataStreamCreator(adminSocket);
            assertNotNull(dsCreator, "DataStreamCreator is null!");
            dsCreator.createDataStream();
            
            DataStream ds = DataStreamInit.dataStream(dsCreator);
            assertNotNull(ds, "DataStream is null!");
            
            MessageSender msgSender = 
                    MessageSenderInit.messageSender(ds.dataOutputStream());
            assertNotNull(msgSender, "MessageSender is null!");
            
            MessageRecipient msgRecipient = 
                    MessageRecipientInit.messageRecipient(ds.dataInputStream());
            assertNotNull(msgRecipient, "MessageRecipient is null!");
            
            MessageCommandDTO msgCmdDTO = 
                    MessageCommandDTOInit.messageCommandDTO(msgSender, msgRecipient);
            assertNotNull(msgCmdDTO, "MessageCommandDTO is null!");
            
            MessageCommandCreator msgCmdCreator
                    = MessageCommandCreatorInit.messageCommandCreator(msgCmdDTO);
            assertNotNull(msgCmdCreator, "MessageCommandCreator is null!");
            
            msgCmdHolder = msgCmdCreator.createMessageCommandHolder();
            assertNotNull(msgCmdHolder, "MessageCommandHolder is null!");
        }
        catch(SocketException | 
                DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
    }
    
    @Test
    public void apply() {
        testBegin("BlacklistMessageProcessor", "apply()");
        
        String adminName = "name";
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
    
    @AfterTest
    public void closeMethod() {
        closeServer = false;
    }
    
    private class TestServerAddBlacklistProcessor implements Runnable {

        private final List<String> blacklist;
        
        public TestServerAddBlacklistProcessor() {
            blacklist = new ArrayList<>();
            blacklist.add("123456789123456");
            blacklist.add("987654321987654");
        }
        
        @Override
        public void run() {
            try(ServerSocket serverSocket = new ServerSocket(50000)) 
            { 
                Socket adminSocket = serverSocket.accept();
                System.out.println("ACCEPT!");
                
                DataStreamCreator dataStreamCreator = 
                        DataStreamCreatorInit.dataStreamCreator(adminSocket);
                try {
                    dataStreamCreator.createDataStream();
                }
                catch(DataStreamCreatorException ex) {
                    System.out.println("CATCH! Message: " + ex.getMessage());
                }
                
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                
                MessageSender msgSender = 
                        MessageSenderInit.messageSender(dataStream.dataOutputStream());
                MessageRecipient msgRecipient = 
                        MessageRecipientInit.messageRecipient(dataStream.dataInputStream());
                
                try {
                    String recMsgIdent = msgRecipient.acceptMessage();
                    System.out.println("Received message: " + recMsgIdent);
                }
                catch(MessageRecipientException ex) {
                    System.out.println("CATCH! Message: " + ex.getMessage());
                }
                
                try {
                    String sendMsg = "{\n";
                    sendMsg += "\"name\": \"name\",\n";
                    sendMsg += "\"isDone\": \"True\",\n";
                    sendMsg += "\"data\": [\n";
                    for(int i = 0; i < blacklist.size(); i++) {
                        String IMEI = blacklist.get(i);
                        sendMsg += "{\"IMEI\": \"" + IMEI + "\"}";
                        if(i < (blacklist.size() - 1))
                            sendMsg += ",\n";
                        else
                            sendMsg += "\n";
                    }
                    sendMsg += "]\n}\n";
                    System.out.println("Send message: " + sendMsg);
                    msgSender.sendMessage(sendMsg);
                    
                    while(closeServer) { /* Just waiting for the end of test */ }
                }
                catch(MessageSenderException ex) {
                    System.out.println("CATCH! Message: " + ex.getMessage());
                }
            }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            System.out.println("Test server off");
        }   
    }
}