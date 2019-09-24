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
package lightsearch.admin.panel.session;

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
import static test.message.TestMessage.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author ViiSE
 */
public class AdminPanelSessionCreatorTestNG {
    
    private AdminPanelPrinter printer;
    private ScannerConnectionDTO scannerConnectionDTO;
    private AdminPanelDTO adminPanelDTO;
    
    @BeforeClass
    public void setUpMethod() {
        printer = AdminPanelPrinterInit.adminPanelPrinter();
        
        ScannerConnectionDTOCreator scannerDTOCreator = ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        assertNotNull(scannerDTOCreator, "ScannerDTOCreator is null!");
        scannerConnectionDTO = scannerDTOCreator.createScannerConnectionDTO();
        
        AdminPanelDTOCreator admPanelDTOCreator = AdminPanelDTOCreatorInit.adminPanelDTOCreator();
        assertNotNull(admPanelDTOCreator, "AdminPanelDTOCreator is null!");
        adminPanelDTO = admPanelDTOCreator.createAdminPanelDTO();
    }
    
    @Test
    @Parameters({"port"})
    public void createSession(int port) {
        testBegin("AdminPanelSessionCreator", "createSession()");
        
        assertNotNull(printer, "AdminPanelPrinter is null!");
        assertNotNull(printer, "ScannerConnectionDTO is null!");
        assertNotNull(printer, "adminPanelDTO is null!");
        
        Thread serverThread = new Thread(new TestLightSearchServer(port));
        serverThread.start();
        
        AdminPanelSessionCreator sessionCreator = AdminPanelSessionCreatorInit.adminPanelSessionCreatorInteractive(
                        printer, scannerConnectionDTO, adminPanelDTO);
        assertNotNull(sessionCreator, "AdminPanelSessionCreator is null!");
        AdminPanelSession session = sessionCreator.createSession();
        assertNotNull(session, "AdminPanelSession is null!");
        
        System.out.println("Session is created. Session: " + session);
        
        testEnd("AdminPanelSessionCreator", "createSession()");
    }
    
    public static class TestLightSearchServer implements Runnable {

        private final int port;

        TestLightSearchServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try(ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("ServerTest Started");
                Socket clientSocket = serverSocket.accept();
                System.out.println("ACCEPT!");

                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);

                MessageSender msgSender = MessageSenderInit.messageSender(dataStream.dataOutputStream());
                MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(dataStream.dataInputStream());
                
                String recMsgIdent = msgRecipient.acceptMessage();
                System.out.println("Received message: " + recMsgIdent);

                String sendMsg = "OK";
                System.out.println("Send message: " + sendMsg);
                msgSender.sendMessage(sendMsg);

                String recMsg = msgRecipient.acceptMessage();
                System.out.println("Received message: " + recMsg);

            }
            catch(IOException | DataStreamCreatorException | MessageRecipientException | MessageSenderException ex) {
                catchMessage(ex);
            }
        }
    }
}
