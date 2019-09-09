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
package test.admin.panel.cmd.admin.processor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreatorInit;
import lightsearch.admin.panel.cmd.admin.AdminCommandEnum;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminDAO;
import lightsearch.admin.panel.data.AdminDAOInit;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminDTOInit;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.MessageCommandDTO;
import lightsearch.admin.panel.data.MessageCommandDTOInit;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreator;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreatorInit;
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
import lightsearch.admin.panel.util.MapRemover;
import lightsearch.admin.panel.util.MapRemoverInit;
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
public class ClientListRequestProcessorTestNG {
    
    private Thread testServerTh;
    private boolean closeServer = true;
    private final String CLIENT_LIST = AdminCommandEnum.CLIENT_LIST.stringValue();
    
    private Map<String, Function<AdminPanelDTO, String>> admCmdHolder;
    private AdminPanelDTO adminPanelDTO;
    
    @BeforeTest
    public void setUpMethod() {
        testServerTh = new Thread(new TestServerClientListRequestProcessor());
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
            
            Map<String, Function<AdminCommandDAO, CommandResult>> msgCmdHolder 
                    = msgCmdCreator.createMessageCommandHolder();
            assertNotNull(msgCmdHolder, "MessageCommandHolder is null!");
            
            AdminDTO adminDTO = 
                    AdminDTOInit.adminDTO(adminSocket, adminDAO, printer, msgCmdHolder);
            assertNotNull(adminDTO, "AdminDTO is null!");
            
            adminDTO.adminDAO().setName("name");
            assertNotNull(adminDTO.adminDAO().name(), "AdminDTO: adminDAO: name is null!");
            assertFalse(adminDTO.adminDAO().name().isEmpty(), 
                    "AdminDTO: adminDAO: name is null!");
            
            MapRemover mapRemover = MapRemoverInit.mapRemover();
            assertNotNull(mapRemover, "MapRemover is null!");
            
            AdminCommandCreator admCmdCreator = 
                    AdminCommandCreatorInit.adminCommandCreator(adminDTO, mapRemover);
            assertNotNull(admCmdCreator, "AdminCommandCreator is null!");
            
            admCmdHolder = admCmdCreator.createCommandHolder();
            assertNotNull(admCmdHolder, "AdminCommandHolder is null!");
            
            AdminPanelDTOCreator admPanelDTOCreator = 
                    AdminPanelDTOCreatorInit.adminPanelDTOCreator();
            assertNotNull(admPanelDTOCreator, "AdminPanelDTOCreator is null!");
            
            adminPanelDTO = admPanelDTOCreator.createAdminPanelDTO();
            assertNotNull(adminPanelDTO, "AdminPanelDTO is null!");
        }
        catch(SocketException | 
                DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
    }
    
    @Test
    public void apply() {
        testBegin("ClientListRequestProcessor", "apply()");
        
        Function<AdminPanelDTO, String> processor = admCmdHolder.get(CLIENT_LIST);
        assertNotNull(processor, "ClientListRequestProcessor is null!");
        
        String result = processor.apply(adminPanelDTO);
        assertNotNull(result, "Result is null!");
        assertFalse(result.isEmpty(), "Result is null!");
        
        System.out.println("Result:");
        System.out.println(result);
        System.out.println("AdminPanelDTO: clients: " + adminPanelDTO.clients());
        
        testEnd("ClientListRequestProcessor", "apply()");
    }
    
    @AfterTest
    public void closeMethod() {
        closeServer = false;
    }
    
    private class TestServerClientListRequestProcessor implements Runnable {

        private final Map<String, String> clients;
        
        public TestServerClientListRequestProcessor() {
            clients = new HashMap<>();
            clients.put("123456789123456", "client1");
            clients.put("987654321987654", "client2");
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
                    final StringBuilder sendMsg = new StringBuilder();
                    sendMsg.append("{\n");
                    sendMsg.append("\"name\": \"name\"\n");
                    sendMsg.append("\"isDone\": \"True\"\n");
                    sendMsg.append("\"data\": [\n");
                    clients.forEach((IMEI, name) -> {
                        sendMsg.append("{\n");
                        sendMsg.append("\"username\": \"").append(name).append("\"\n");
                        sendMsg.append("\"IMEI\": \"").append(IMEI).append("\"\n");
                        sendMsg.append("}\n"); 
                    });
                    sendMsg.append("]\n}\n");
                    System.out.println("Send message: " + sendMsg.toString());
                    
                    msgSender.sendMessage(sendMsg.toString());
                    
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
