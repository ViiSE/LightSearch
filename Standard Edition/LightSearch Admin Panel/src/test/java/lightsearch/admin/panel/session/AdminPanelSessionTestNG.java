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
import static test.message.TestMessage.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

/**
 *
 * @author ViiSE
 */
public class AdminPanelSessionTestNG {
    
    private AdminPanelSessionDTO sessionDTO;
    
    @BeforeClass
    @Parameters({"ip", "port", "openTest"})
    public void setUpMethod(String ip, int port, boolean openTest) {
        Thread testServerThread = new Thread(new TestLightSearchServer(port));
        testServerThread.start();

        AdminPanelMenu menu = AdminPanelMenuCreatorInit.adminMenuCreator().createAdminMenu();
        assertNotNull(menu, "AdminPanelMenu is null!");

        AdminPanelDTO admPanelDTO = AdminPanelDTOCreatorInit.adminPanelDTOCreator().createAdminPanelDTO();
        assertNotNull(admPanelDTO, "AdminPanelDTO is null!");

        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        assertNotNull(printer, "AdminPanelPrinter is null!");

        AdminCommandCreator admCmdCreator =
                DataProviderCreator.createDataProvider(AdminCommandCreator.class, ip, port, openTest, printer);
        assertNotNull(admCmdCreator, "AdminCommandCreator is null!");

        ScannerChooserCommand scannerCmd = DataProviderCreator.createDataProvider(ScannerChooserCommand.class);
        assertNotNull(scannerCmd, "ScannerChooserCommand is null!");

        sessionDTO = AdminPanelSessionDTOInit.adminPanelDTO(
                menu, admPanelDTO, admCmdCreator.createCommandHolder(), printer, scannerCmd);
    }
    
    @Test
    public void startSession() {
        testBegin("AdminPanelSession", "startSession()");
        
        assertNotNull(sessionDTO, "AdminPanelSessionDTO is null!");
        AdminPanelSession admPanelSession = AdminPanelSessionInit.adminPanelSession(sessionDTO);
        admPanelSession.startSession();
        
        testEnd("AdminPanelSession", "startSession()");
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

            } catch(IOException | DataStreamCreatorException | MessageRecipientException | MessageSenderException ex) {
                catchMessage(ex);
            }
        }
    }
}
