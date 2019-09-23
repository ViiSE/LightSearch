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
package lightsearch.admin.panel.cmd.admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreatorInit;
import lightsearch.admin.panel.cmd.admin.processor.TestServer;
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
import static test.message.TestMessage.*;

import org.testng.annotations.*;
import test.data.DataProviderCreator;

/**
 *
 * @author ViiSE
 */
public class AdminCommandCreatorTestNG {

    private AdminDTO adminDTO;
    private MapRemover mapRemover;
    
    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpMethod(String ip, int port) {
        TestServer.closeServer = false;
        TestServer ts = new TestServer(port, null);
        ts.setSimpleMode(true);
        Thread testServerTh = new Thread(ts);
        testServerTh.start();

        adminDTO = DataProviderCreator.createDataProvider(AdminDTO.class, ip, port);
        assertNotNull(adminDTO, "AdminDTO is null!");

        mapRemover = MapRemoverInit.mapRemover();
        assertNotNull(mapRemover, "MapRemover is null!");
    }
    
    @Test
    public void createCommandHolder() {
        testBegin("AdminCommandCreator", "createCommandHolder()");
        
        AdminCommandCreator admCmdCreator = AdminCommandCreatorInit.adminCommandCreator(adminDTO, mapRemover);
        assertNotNull(admCmdCreator.createCommandHolder(), "AdminCommandHolder is null!");
        
        System.out.println("AdminCommandHolderCreator: createCommandHolder() :" + admCmdCreator.createCommandHolder());
        
        testEnd("AdminCommandCreator", "createCommandHolder()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.closeServer = true;
    }
}
