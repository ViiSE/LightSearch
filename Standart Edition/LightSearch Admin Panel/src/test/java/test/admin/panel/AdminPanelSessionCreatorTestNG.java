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
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreator;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreatorInit;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import lightsearch.admin.panel.exception.MessageRecipientException;
import lightsearch.admin.panel.exception.MessageSenderException;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageRecipientInit;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import lightsearch.admin.panel.session.AdminPanelSession;
import lightsearch.admin.panel.session.AdminPanelSessionCreator;
import lightsearch.admin.panel.session.AdminPanelSessionCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminPanelSessionCreatorTestNG {
    
    private AdminPanelPrinter printer;
    private ScannerConnectionDTO scannerConnectionDTO;
    private AdminPanelDTO adminPanelDTO;
    
    @BeforeTest
    public void setUpMethod() throws Exception {
        printer = AdminPanelPrinterInit.adminPanelPrinter();
        
        ScannerConnectionDTOCreator scannerDTOCreator = 
                ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        assertNotNull(scannerDTOCreator, "ScannerDTOCreator is null!");
        scannerConnectionDTO = 
                scannerDTOCreator.createScannerConnectionDTO();
        
        AdminPanelDTOCreator admPanelDTOCreator = 
                AdminPanelDTOCreatorInit.adminPanelDTOCreator();
        assertNotNull(admPanelDTOCreator, "AdminPanelDTOCreator is null!");
        adminPanelDTO = admPanelDTOCreator.createAdminPanelDTO();
    }
    
    @Test
    public void createSession() {
        testBegin("AdminPanelSessionCreator", "createSession()");
        
        assertNotNull(printer, "AdminPanelPrinter is null!");
        assertNotNull(printer, "ScannerConnectionDTO is null!");
        assertNotNull(printer, "adminPanelDTO is null!");
        
        Thread serverThread = new Thread(new TestLightSearchServer());
        serverThread.start();
        
        AdminPanelSessionCreator sessionCreator = 
                AdminPanelSessionCreatorInit.adminPanelSessionCreatorInteractive(
                        printer, scannerConnectionDTO, adminPanelDTO);
        assertNotNull(sessionCreator, "AdminPanelSessionCreator is null!");
        AdminPanelSession session = sessionCreator.createSession();
        assertNotNull(session, "AdminPanelSession is null!");
        
        System.out.println("Session is created. Session: " + session);
        
        testEnd("AdminPanelSessionCreator", "createSession()");
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
