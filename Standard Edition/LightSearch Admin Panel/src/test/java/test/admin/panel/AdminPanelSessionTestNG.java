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
import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreatorInit;
import lightsearch.admin.panel.cmd.message.MessageCommandCreator;
import lightsearch.admin.panel.cmd.message.MessageCommandCreatorInit;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.AdminDAO;
import lightsearch.admin.panel.data.AdminDAOInit;
import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.data.AdminDTOInit;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreator;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreatorInit;
import lightsearch.admin.panel.data.AdminPanelSessionDTO;
import lightsearch.admin.panel.data.AdminPanelSessionDTOInit;
import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.MessageCommandDTO;
import lightsearch.admin.panel.data.MessageCommandDTOInit;
import lightsearch.admin.panel.data.ScannerChooserCommandDTO;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreatorInit;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import lightsearch.admin.panel.exception.MessageRecipientException;
import lightsearch.admin.panel.exception.MessageSenderException;
import lightsearch.admin.panel.menu.AdminPanelMenu;
import lightsearch.admin.panel.menu.AdminPanelMenuCreator;
import lightsearch.admin.panel.menu.AdminPanelMenuCreatorInit;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageRecipientInit;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import lightsearch.admin.panel.scanner.ScannerChooserCommand;
import lightsearch.admin.panel.scanner.ScannerChooserCommandInit;
import lightsearch.admin.panel.session.AdminPanelSession;
import lightsearch.admin.panel.session.AdminPanelSessionInit;
import lightsearch.admin.panel.socket.SocketCreator;
import lightsearch.admin.panel.socket.SocketCreatorInit;
import lightsearch.admin.panel.util.MapRemover;
import lightsearch.admin.panel.util.MapRemoverInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminPanelSessionTestNG {
    
    private AdminPanelSessionDTO sessionDTO;
    private Thread testServerThread;
    
    private void startTestServer() {
        testServerThread = new Thread(new TestLightSearchServer());
        testServerThread.start();
    }
    
    private AdminPanelMenu createMenu() {
        AdminPanelMenuCreator menuCreator = 
                AdminPanelMenuCreatorInit.adminMenuCreator();
        assertNotNull(menuCreator, "AdminPanelMenuCreator is null!");
        AdminPanelMenu menu = menuCreator.createAdminMenu();
        assertNotNull(menu, "AdminPanelMenu is null!");
        
        return menu;
    }
    
    private AdminPanelDTO createAdminPanelDTO() {
        AdminPanelDTOCreator admPanelDTOCreator = 
                AdminPanelDTOCreatorInit.adminPanelDTOCreator();
        assertNotNull(admPanelDTOCreator, "AdminPanelDTOCreator is null!");
        AdminPanelDTO admPanelDTO = admPanelDTOCreator.createAdminPanelDTO();
        assertNotNull(admPanelDTO, "AdminPanelDTO is null!");
        
        return admPanelDTO;
    }
    
    private AdminPanelPrinter createAdminPanelPrinter() {
        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        assertNotNull(printer, "AdminPanelPrinter is null!");
        
        return printer;
    }
    
    private AdminCommandCreator createAdminCommandCreator(AdminPanelPrinter printer)
            throws Exception {
        ScannerConnectionDTOCreator scConnDTOCreator = 
                ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        assertNotNull(scConnDTOCreator, "ScannerConnectionDTOCreator is null!");
        ScannerConnectionDTO scConnDTO = 
                scConnDTOCreator.createScannerConnectionDTO();
        assertNotNull(scConnDTO, "ScannerConnectionDTO is null!");
        
        ConnectionDTOCreator connDTOCreator = 
                ConnectionDTOCreatorInit.connectionDTOCreator(printer, scConnDTO);
        assertNotNull(connDTOCreator, "ConnectionDTOCreator is null!");
        ConnectionDTO connDTO = connDTOCreator.createConnectionDTO();
        assertNotNull(connDTO, "ConnectionDTO is null!");
        
        SocketCreator socketCreator = SocketCreatorInit.socketCreator(connDTO);
        assertNotNull(socketCreator, "SocketCreator is null!");
        Socket adminSocket = socketCreator.createSocket();
        
        DataStreamCreator dsCreator = 
                DataStreamCreatorInit.dataStreamCreator(adminSocket);
        assertNotNull(dsCreator, "DataStreamCreator is null!");
        try { 
            dsCreator.createDataStream();
        }
        catch(DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        assertNotNull(dsCreator, "DataStreamCreator is null!");
        
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
        
        MessageCommandCreator msgCmdCreator = 
                MessageCommandCreatorInit.messageCommandCreator(msgCmdDTO);
        assertNotNull(msgCmdCreator, "MessageCommandCreator is null!");
        assertNotNull(msgCmdCreator.createMessageCommandHolder(),
                "MessageCommandHolder is null!");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        assertNotNull(adminDAO, "AdminDAO is null!");
        
        AdminDTO adminDTO = AdminDTOInit.adminDTO(
                adminSocket, 
                adminDAO, 
                printer, 
                msgCmdCreator.createMessageCommandHolder()
        );
        assertNotNull(adminDTO, "AdminDTO is null");
        
        MapRemover mapRemover = MapRemoverInit.mapRemover();
        assertNotNull(mapRemover, "MapRemover is null!");
        
        AdminCommandCreator admCmdCreator = 
                AdminCommandCreatorInit.adminCommandCreator(adminDTO, mapRemover);
        assertNotNull(admCmdCreator, "AdminCommandCreator is null!");
        assertNotNull(admCmdCreator.createCommandHolder(), 
                "AdminCommandHolder is null!");
        
        return admCmdCreator;
    }
    
    private ScannerChooserCommand createScannerChooserCommand() {
        ScannerChooserCommandDTOCreator scChCmdCreator = 
                ScannerChooserCommandDTOCreatorInit.scannerChooserCommandDTOCreator();
        assertNotNull(scChCmdCreator, "ScannerChooserCommandDTOCreator is null!");
        
        ScannerChooserCommandDTO scChCmdDTO = 
                scChCmdCreator.createScannerChooserCommandDTO();
        assertNotNull(scChCmdDTO, "ScannerChooserCommandDTO is null!");
        
        ScannerChooserCommand scannerCmd = 
                ScannerChooserCommandInit.scannerChooserCommand(scChCmdDTO);
        assertNotNull(scannerCmd, "ScannerChooserCommand is null!");
        
        return scannerCmd;
    }
    
    @BeforeTest
    public void setUpMethod() throws Exception {
        startTestServer();
        
        AdminPanelMenu menu               = createMenu();
        AdminPanelDTO adminPanelDTO       = createAdminPanelDTO();
        AdminPanelPrinter printer         = createAdminPanelPrinter();
        AdminCommandCreator admCmdCreator = createAdminCommandCreator(printer);
        ScannerChooserCommand scannerCmd  = createScannerChooserCommand();
        
        sessionDTO = AdminPanelSessionDTOInit.adminPanelDTO(
                menu,
                adminPanelDTO,
                admCmdCreator.createCommandHolder(),
                printer,
                scannerCmd
        );
    }
    
    @Test
    public void startSession() {
        testBegin("AdminPanelSession", "startSession()");
        
        assertNotNull(sessionDTO, "AdminPanelSessionDTO is null!");
        AdminPanelSession admPanelSession = 
                AdminPanelSessionInit.adminPanelSession(sessionDTO);
        admPanelSession.startSession();
        
        testEnd("AdminPanelSession", "startSession()");
    }
    
    public class TestLightSearchServer implements Runnable {

        @Override
        public void run() {
            try(ServerSocket serverSocket = new ServerSocket(50000);) {
                System.out.println("ServerTest Started");
                Socket clientSocket = serverSocket.accept();
                System.out.println("ACCEPT!");
                DataStreamCreator dataStreamCreator = 
                        DataStreamCreatorInit.dataStreamCreator(clientSocket);
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
                    String sendMsg = "OK";
                    System.out.println("Send message: " + sendMsg);
                    msgSender.sendMessage(sendMsg);
                }
                catch(MessageSenderException ex) {
                    System.out.println("CATCH! Message: " + ex.getMessage());
                }
                
                try {
                    String recMsg = msgRecipient.acceptMessage();
                    System.out.println("Received message: " + recMsg);
                }
                catch(MessageRecipientException ex) {
                    System.out.println("CATCH! Message: " + ex.getMessage());
                }
            }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
        }
    }
}
