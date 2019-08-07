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
package test.admin.panel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.function.Function;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminDAO;
import lightsearch.admin.panel.data.AdminDAOInit;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminDTOInit;
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
public class AdminDTOTestNG {
    
    private Thread testServerTh;
    private boolean closeServer = true;
    
    private Socket adminSocket;
    private AdminDAO adminDAO;
    private AdminPanelPrinter printer;
    private Map<String, Function<AdminCommandDAO, CommandResult>> msgCmdHolder;
    
    @BeforeTest
    public void setUpMethod() {
        testServerTh = new Thread(new TestServer());
        testServerTh.start();
        
        printer = AdminPanelPrinterInit.adminPanelPrinter();
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
            adminSocket = admSocketCreator.createSocket();
            assertNotNull(adminSocket, "Socket is null!");

            adminDAO = AdminDAOInit.adminDAO();
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
    public void adminSocket() {
        testBegin("AdminDTO", "adminSocket()");
        
        AdminDTO admDTO = AdminDTOInit.adminDTO(adminSocket, adminDAO, printer, msgCmdHolder);
        assertNotNull(admDTO, "AdminDTO is null!");
        assertNotNull(admDTO.adminSocket(), "AdminDTO: adminSocket() is null!");
        
        System.out.println("AdminDTO.adminSocket(): " + admDTO.adminSocket());
        
        testEnd("AdminDTO", "adminSocket()");
    }
    
    @Test
    public void adminDAO() {
        testBegin("AdminDTO", "adminDAO()");
        
        AdminDTO admDTO = AdminDTOInit.adminDTO(adminSocket, adminDAO, printer, msgCmdHolder);
        assertNotNull(admDTO, "AdminDTO is null!");
        assertNotNull(admDTO.adminSocket(), "AdminDTO: adminDAO() is null!");
        
        System.out.println("AdminDTO.adminDAO(): " + admDTO.adminDAO());
        
        testEnd("AdminDTO", "adminDAO()");
    }
    
    @Test
    public void printer() {
        testBegin("AdminDTO", "printer()");
        
        AdminDTO admDTO = AdminDTOInit.adminDTO(adminSocket, adminDAO, printer, msgCmdHolder);
        assertNotNull(admDTO, "AdminDTO is null!");
        assertNotNull(admDTO.printer(), "AdminDTO: printer() is null!");
        
        System.out.println("AdminDTO.printer(): " + admDTO.printer());
        
        testEnd("AdminDTO", "printer()");
    }
    
    @Test
    public void messageCommandHolder() {
        testBegin("AdminDTO", "messageCommandHolder()");
        
        AdminDTO admDTO = AdminDTOInit.adminDTO(adminSocket, adminDAO, printer, msgCmdHolder);
        assertNotNull(admDTO, "AdminDTO is null!");
        assertNotNull(admDTO.messageCommandHolder(), "AdminDTO: messageCommandHolder() is null!");
        
        System.out.println("AdminDTO.messageCommandHolder(): " + admDTO.messageCommandHolder());
        
        testEnd("AdminDTO", "messageCommandHolder()");
    }
    
    @AfterTest
    public void closeMethod() {
        closeServer = false;
    }
    
    private class TestServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            
            try { serverSocket = new ServerSocket(50000); }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            System.out.println("Test server on");
            
            try { if(serverSocket != null) serverSocket.accept(); }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            while(closeServer) { /* Just waiting for the end of test */ }
            
            try { if(serverSocket != null) serverSocket.close(); }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            System.out.println("Test server off");
        }   
    }
}
