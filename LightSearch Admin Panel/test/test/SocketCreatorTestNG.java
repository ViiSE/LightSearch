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
import java.net.Socket;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreatorInit;
import lightsearch.admin.panel.exception.SocketException;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import lightsearch.admin.panel.socket.SocketCreator;
import lightsearch.admin.panel.socket.SocketCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SocketCreatorTestNG {
    
    private ConnectionDTO connectionDTO;
    
    @BeforeTest
    public void setUpMethod() throws Exception {
        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        assertNotNull(printer, "AdminPanelPrinter is null!");
        
        ScannerConnectionDTOCreator scConnDTOCreator = 
                ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        assertNotNull(scConnDTOCreator, "ScannerConnectionDTOCreator is null!");
        ScannerConnectionDTO scConnDTO = scConnDTOCreator.createScannerConnectionDTO();
        
        ConnectionDTOCreator connDTOCreator = 
                ConnectionDTOCreatorInit.connectionDTOCreator(printer, scConnDTO);
        assertNotNull(connDTOCreator, "ConnectionDTOCreator is null!");
        connectionDTO = connDTOCreator.createConnectionDTO();
    }
    
    @Test
    public void createSocket() {
        testBegin("SocketCreator", "createSocket()");
        
        try {
            SocketCreator socketCreator = SocketCreatorInit.socketCreator(connectionDTO);
            try (Socket socket = socketCreator.createSocket()) {
                System.out.println("Socket created: socket = " + socket);
            }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
        }
        catch(SocketException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("SocketCreator", "createSocket()");
    }
}
