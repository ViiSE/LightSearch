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
package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreatorInit;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
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
public class AdminCommandCreatorTestNG {
    
    private Thread testServerTh;
    private boolean closeServer = true;
    
    private AdminDTO adminDTO;
    private MapRemover mapRemover;
    
    @BeforeTest
    public void setUpMethod() {
        testServerTh = new Thread(new TestServer());
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
            
            assertNotNull(msgCmdCreator.createMessageCommandHolder(), 
                    "MessageCommandHolder is null!");
            
            msgCmdCreator.createMessageCommandHolder();
            adminDTO = AdminDTOInit.adminDTO(adminSocket, adminDAO, printer,
                    msgCmdCreator.createMessageCommandHolder());
            assertNotNull(adminDTO, "AdminDTO is null!");
            
            mapRemover = MapRemoverInit.mapRemover();
            assertNotNull(mapRemover, "MapRemover is null!");
        }
        catch(SocketException | 
                DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
    }
    
    @Test
    public void createCommandHolder() {
        testBegin("AdminCommandCreator", "createCommandHolder()");
        
        AdminCommandCreator admCmdCreator = 
                AdminCommandCreatorInit.adminCommandCreator(adminDTO, mapRemover);
        assertNotNull(admCmdCreator.createCommandHolder(), 
                "AdminCommandHolder is null!");
        
        System.out.println("AdminCommandHolderCreator: createCommandHolder() :"
                + admCmdCreator.createCommandHolder());
        
        testEnd("AdminCommandCreator", "createCommandHolder()");
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
